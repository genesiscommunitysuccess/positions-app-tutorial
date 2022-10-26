/**
 * System              : Genesis Business Library
 * Sub-System          : multi-pro-code-test Configuration
 * Version             : 1.0
 * Copyright           : (c) Genesis
 * Date                : 2022-03-18
 * Function : Provide event handler config for multi-pro-code-test.
 *
 * Modification History
 */

import global.genesis.TradeStateMachine
import global.genesis.commons.standards.GenesisPaths
import global.genesis.gen.view.repository.TradeViewAsyncRepository
import global.genesis.jackson.core.GenesisJacksonMapper
import global.genesis.jackson.core.GenesisJacksonMapper.Companion.toJsonString
import global.genesis.message.event.PositionCancel
import global.genesis.message.event.PositionReport
import global.genesis.message.event.TradeAllocated
import global.genesis.message.event.TradeCancelled
import java.io.File
import java.time.LocalDate

eventHandler {
    val stateMachine = inject<TradeStateMachine>()
    val tradeViewRepo = inject<TradeViewAsyncRepository>()

    eventHandler<Trade>(name = "TRADE_INSERT") {
        onCommit { event ->
            val trade = event.details
            stateMachine.insert(entityDb, trade)
            ack()
        }
    }
    eventHandler<Trade>(name = "TRADE_MODIFY") {
        onCommit { event ->
            val trade = event.details
            stateMachine.modify(entityDb, trade)
            ack()
        }
    }
    eventHandler<TradeCancelled>(name = "TRADE_CANCELLED") {
        onCommit { event ->
            val message = event.details
            stateMachine.modify(entityDb, message.tradeId) { trade ->
                trade.tradeStatus = TradeStatus.CANCELLED
            }
            ack()
        }
    }
    eventHandler<TradeAllocated>(name = "TRADE_ALLOCATED") {
        onCommit { event ->
            val message = event.details
            stateMachine.modify(entityDb, message.tradeId) { trade ->
                trade.tradeStatus = TradeStatus.ALLOCATED
            }
            ack()
        }
    }
    eventHandler<PositionCancel>(name = "POSITION_CANCEL", transactional = true) {
        onCommit { event ->
            val positionId = event.details.positionId
            entityDb.insert(
                Notify {
                    topic = "PositionAlert"
                    header = "Position Alert for $positionId"
                    body = mapOf<String, Any?>(
                        "emailDistribution" to mapOf(
                            "to" to listOf("dev-training@freesmtpserver.com"),
                            "cc" to emptyList(),
                            "bcc" to emptyList(),
                        ),
                        "content" to "Position $positionId breached the limit"
                    ).toJsonString(true)
                }
            )
            ack()
        }
    }
    eventHandler<PositionReport>(name = "EVENT_POSITION_REPORT", transactional = true) {
        onCommit {
            val mapper = GenesisJacksonMapper.csvWriter<TradeView>()
            val today = LocalDate.now().toString()
            val positionReportFolder = File(GenesisPaths.runtime()).resolve("position-30seconds-report")
            if (!positionReportFolder.exists()) positionReportFolder.mkdirs()

            tradeViewRepo.getBulk()
                .toList()
                .groupBy { it.counterpartyCounterpartyName }
                .forEach { (counterParty, trades) ->
                    val file = positionReportFolder.resolve("${counterParty}_$today.csv")
                    if (file.exists()) file.delete()
                    mapper.writeValues(file).use { it.writeAll(trades) }
                }
            ack()
        }
    }
}