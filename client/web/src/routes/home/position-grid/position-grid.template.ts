import { html, repeat } from '@microsoft/fast-element';
import { positionColumnDefs } from '../positionColumnDefs';
import { PositionGrid } from './position-grid';

export const positionsGridTemplate = html<PositionGrid>`
  <template>
    <zero-grid-pro persist-column-state-key="position-grid-settings">
      <grid-pro-genesis-datasource
        resource-name="ALL_POSITIONS"
        order-by="INSTRUMENT_ID"
      ></grid-pro-genesis-datasource>
      ${repeat(
        () => positionColumnDefs,
        html`
          <grid-pro-column :definition="${(x) => x}"></grid-pro-column>
        `
      )}
      <grid-pro-column :definition="${(x) => x.singlePositionActionColDef}"></grid-pro-column>
    </zero-grid-pro>
  </template>
`;
