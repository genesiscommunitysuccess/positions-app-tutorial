import { customElement, FASTElement, observable } from '@microsoft/fast-element';
import { HomeTemplate as template } from './home.template';
import { HomeStyles as styles } from './home.styles';
import { FoundationLayout } from '@genesislcap/foundation-layout';
import { HOME_DEFAULT_LAYOUT } from './predefined-layouts';

const name = 'home-route';

@customElement({
  name,
  template,
  styles,
})
export class Home extends FASTElement {
  layout: FoundationLayout;

  connectedCallback(): void {
    super.connectedCallback();
    this.resetLayout = this.resetLayout.bind(this);
  }

  resetLayout() {
    this.layout.loadLayout(JSON.parse(HOME_DEFAULT_LAYOUT));
  }

  constructor() {
    super();
  }
}
