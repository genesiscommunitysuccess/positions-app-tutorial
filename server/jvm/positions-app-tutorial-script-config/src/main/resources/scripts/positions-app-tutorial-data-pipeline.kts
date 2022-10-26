import global.genesis.gen.config.sysdef.SysDef.location
import global.genesis.gen.config.tables.TRADE
import global.genesis.gen.config.tables.TRADE.COUNTERPARTY_ID
import global.genesis.gen.config.tables.TRADE.ENTERED_BY
import global.genesis.gen.config.tables.TRADE.INSTRUMENT_ID
import global.genesis.gen.config.tables.TRADE.PRICE
import global.genesis.gen.config.tables.TRADE.QUANTITY
import global.genesis.gen.config.tables.TRADE.SIDE
import global.genesis.gen.config.tables.TRADE.TRADE_DATETIME
import global.genesis.gen.config.tables.TRADE.TRADE_STATUS

sources {
    csv("some-name") {
        location = "file:/root/genesis_home/runtime/fileIngress?fileName=trades.csv"

        mapper("mapper-name", TRADE) {
            INSTRUMENT_ID {
                sourceProperty = "instrumentId"
            }
            COUNTERPARTY_ID {
                sourceProperty = "counterpartyId"
            }
            QUANTITY {
                sourceProperty = "amount"
            }
            SIDE {
                sourceProperty = "buySell"
            }
            PRICE {
                sourceProperty = "price"
            }
            TRADE_DATETIME {
                transform {
                    input.get(dateValue("date"))
                }
            }
            ENTERED_BY {
                sourceProperty = "enteredBy"
            }
            TRADE_STATUS {
                transform {
                    TradeStatus.NEW
                }
            }
        }
    }
}