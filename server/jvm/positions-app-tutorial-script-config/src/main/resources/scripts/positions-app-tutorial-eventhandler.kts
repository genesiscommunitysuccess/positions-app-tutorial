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
import global.genesis.message.event.TradeAllocated
import global.genesis.message.event.TradeCancelled

eventHandler {
    val stateMachine = inject<TradeStateMachine>()

    eventHandler<Trade>(name = "TRADE_INSERT", transactional = true) {
        onCommit { event ->
            val trade = event.details
            trade.enteredBy = event.userName
            stateMachine.insert(trade)
            ack()
        }
    }

    eventHandler<TradeCancelled>(name = "TRADE_CANCELLED", transactional = true) {
        onCommit { event ->
            val message = event.details
            stateMachine.modify(message.tradeId) { trade ->
                trade.tradeStatus = TradeStatus.CANCELLED
            }
            ack()
        }
    }

    eventHandler<TradeAllocated>(name = "TRADE_ALLOCATED", transactional = true) {
        onCommit { event ->
            val message = event.details
            stateMachine.modify(message.tradeId) { trade ->
                trade.tradeStatus = TradeStatus.ALLOCATED
            }
            ack()
        }
    }

    eventHandler<Trade>(name = "TRADE_MODIFY", transactional = true) {
        onCommit { event ->
            val trade = event.details
            stateMachine.modify(trade)
            ack()
        }
    }
}