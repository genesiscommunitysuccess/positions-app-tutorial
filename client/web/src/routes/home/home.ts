import {customElement, FASTElement} from '@microsoft/fast-element';
import {HomeTemplate as template} from './home.template';
import {HomeStyles as styles} from './home.styles';
import {observable} from '@microsoft/fast-element';
import {Connect} from '@genesislcap/foundation-comms';

const name = 'home-route';

@customElement({
  name,
  template,
  styles,
})
export class Home extends FASTElement {
  constructor() {
    super();
  }

  @observable public quantity: string;
  @observable public price: string;
  @observable public instrument: string;
  @observable public side: string = 'BUY';
  @observable public tradeInstruments: Array<{value: string, label: string}>;

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

  @Connect connect: Connect;

  public async insertTrade1(event) {
    const formData = event.detail.payload;
    const insertTradeEvent = await this.connect.commitEvent('EVENT_TRADE_INSERT', {
      DETAILS: {
        COUNTERPARTY_ID: 'GENESIS',
        INSTRUMENT_ID: formData.INSTRUMENT_ID,
        QUANTITY: formData.QUANTITY,
        PRICE: formData.PRICE,
        SIDE: formData.SIDE,
        TRADE_DATETIME: Date.now(),
      },
    });
  }

  public async insertTrade2() {
    const insertTradeRequest = await this.connect.commitEvent('EVENT_TRADE_INSERT', {
      DETAILS: {
        COUNTERPARTY_ID: 'GENESIS',
        INSTRUMENT_ID: this.instrument,
        QUANTITY: this.quantity,
        PRICE: this.price,
        SIDE: this.side,
        TRADE_DATETIME: Date.now(),
      },
      IGNORE_WARNINGS: true,
      VALIDATE: false,
    });
  }

  @observable chartsConfiguration = {
    width: 600,
    angleField: 'value',
    colorField: 'type',
    radius: 0.75,
    label: {
      type: 'spider',
      labelHeight: 28,
      content: '{name}\n{percentage}',
      style: {
        fill: 'white',
      },
    },
    interactions: [{ type: 'element-selected' }, { type: 'element-active' }],
  };

  @observable chartsData = [
    { type: 'Exam 1', value: 27 },
    { type: 'Exam 2', value: 25 },
    { type: 'Exam 3', value: 18 },
    { type: 'Exam 4', value: 15 },
    { type: 'Exam 5', value: 10 },
    { type: 'Exam 6', value: 13 },
  ];

  public async connectedCallback() {
    super.connectedCallback();

    const tradeInstrumentsRequest = await this.connect.request('INSTRUMENT');
    this.tradeInstruments = tradeInstrumentsRequest.REPLY?.map(instrument => ({value: instrument.INSTRUMENT_ID, label: instrument.INSTRUMENT_ID}));
    this.instrument = this.tradeInstruments[0].value;
  }
}
