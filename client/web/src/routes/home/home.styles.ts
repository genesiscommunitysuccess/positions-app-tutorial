import {css} from '@microsoft/fast-element';
import {mixinScreen} from '../../styles';

export const HomeStyles = css`
  :host {
    ${mixinScreen('flex')}
    align-items: center;
    justify-content: center;
    flex-direction: column;
    --neutral-stroke-divider-rest: var(--neutral-fill-stealth-rest);
  }

  zero-tabs {
    height: 90%;
    width: 100%;
  }
  zero-tab-panel {
    height: 100%;
  }
  zero-grid-pro {
    height: 100%;
    width: 100%;
  }
  zero-form {
    height: 250px;
    width: 500px
  }
  .container {
    display: flex;
    flex-direction: column;
    height: 100%;
    width: 100%;
    align-items: center;
  }
`;
