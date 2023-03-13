import { customElement, FASTElement } from '@microsoft/fast-element';
import { positionsGridTemplate } from './position-grid.template';

@customElement({
  name: 'position-grid',
  template: positionsGridTemplate,
})
export class PositionGrid extends FASTElement {
  public singlePositionActionColDef = {
    headerName: 'Action',
    minWidth: 120,
    maxWidth: 120,
    cellRenderer: 'action',
    cellRendererParams: {
      actionClick: async (rowData) => {
        console.log(rowData);
      },
      actionName: 'Add Trade',
      appearance: 'primary-gradient',
    },
    pinned: 'right',
  };
}
