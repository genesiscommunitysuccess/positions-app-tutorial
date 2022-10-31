import {html} from '@microsoft/fast-element';
import type {Home} from './home';
import {positionColumnDefs} from './positionColumnDefs';
import {repeat} from '@microsoft/fast-element';
import {sync} from '@genesislcap/foundation-utils';
import {positionGridStyles} from './positionsGrid.styles';

export const HomeTemplate = html<Home>`
    
<zero-tabs class="horizontal" orientation="horizontal" activeindicator="">
    <zero-tab slot="tab">Positions Grid
    </zero-tab>
    <zero-tab slot="tab">Trades Grid
    </zero-tab>
    <zero-tab slot="tab">Trades Form 1
    </zero-tab>
    <zero-tab slot="tab">Trades Form 2
    </zero-tab>
    <zero-tab slot="tab">Positions Chart
    </zero-tab>
    
    <zero-tab-panel slot="tabpanel">
        <div class="container">
            <zero-grid-pro
                    only-template-col-defs
                    persist-column-state-key='position-grid-settings'
            >
                <slotted-styles :styles=${() => positionGridStyles}></slotted-styles>
                <grid-pro-genesis-datasource
                        resourceName="ALL_POSITIONS"
                        orderBy="INSTRUMENT_ID">
                </grid-pro-genesis-datasource>
                ${repeat(() => positionColumnDefs, html`
                    <grid-pro-column :definition="${n => n}"></grid-pro-column>
                `)}
                <grid-pro-column :definition="${n => n.singlePositionActionColDef}"></grid-pro-column>
            </zero-grid-pro>
        </div>    
    </zero-tab-panel>
    <zero-tab-panel slot="tabpanel">
        <div class="container">
            <zero-grid-pro>
                <grid-pro-genesis-datasource
                        resourceName="ALL_TRADES"
                        orderBy="INSTRUMENT_ID">
                </grid-pro-genesis-datasource>
            </zero-grid-pro>
        </div>
    </zero-tab-panel>
    <zero-tab-panel slot="tabpanel">
        <div class="container">
            <zero-form
                    resourceName="EVENT_TRADE_INSERT"
                    @submit=${(y, c) => y.insertTrade1(c.event as CustomEvent)}
            ></zero-form>
        </div>
    </zero-tab-panel>
    <zero-tab-panel slot="tabpanel">
        <div class="container">
            <zero-text-field
                    :value=${sync(x=> x.quantity)}
            >
                Quantity
            </zero-text-field>
            <zero-text-field
                    :value=${sync(x=> x.price)}
            >
                Price
            </zero-text-field>
            <span>Instrument</span>
            <zero-select :value=${sync(x => x.instrument)}>
                ${repeat(x => x.tradeInstruments, html`
                <zero-option value=${x => x.value}>${x => x.label}</zero-option>
                `)}
            </zero-select>
            <span>Side</span>
            <zero-select :value=${sync(x=> x.side)}>
                <zero-option>BUY</zero-option>
                <zero-option>SELL</zero-option>
            </zero-select>
            <zero-button @click=${x=> x.insertTrade2()}>Add Trade</zero-button>
        </div>
    </zero-tab-panel>
    <zero-tab-panel slot="tabpanel">
        <zero-charts type="pie" :config=${x => x.chartsConfiguration}>
            <charts-datasource
                    resourceName="ALL_POSITIONS"
                    server-fields="INSTRUMENT_ID VALUE"
                    charts-fields="type value"
                    isSnapshot
            ></charts-datasource>
        </zero-charts>
    </zero-tab-panel>

</zero-tabs>
`;
