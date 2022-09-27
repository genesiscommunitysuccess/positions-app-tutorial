import {html} from '@microsoft/fast-element';
import type {Home} from './home';
import {positionColumnDefs} from './positionColumnDefs';
import {repeat} from '@microsoft/fast-element';


export const HomeTemplate = html<Home>`
<zero-ag-grid
    style="width: 100%; height: 100%"
    only-template-col-defs
    persist-column-state-key='position-grid-settings'
    >
    <ag-genesis-datasource
        resourceName="ALL_POSITIONS"
        orderBy="INSTRUMENT_ID">
    </ag-genesis-datasource>
    ${repeat(() => positionColumnDefs, html`
    <ag-grid-column :definition="${x => x}"></ag-grid-column>
    `)}
    <ag-grid-column :definition="${x => x.singlePositionActionColDef}" />
</zero-ag-grid>
`;
