import { html } from '@microsoft/fast-element';
import { TradesGrid } from './trades-grid';

export const tradesGridTemplate = html<TradesGrid>`
  <template>
    <zero-grid-pro>
      <grid-pro-genesis-datasource
        resource-name="ALL_TRADES"
        order-by="INSTRUMENT_ID"
      ></grid-pro-genesis-datasource>
    </zero-grid-pro>
  </template>
`;
