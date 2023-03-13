import { html } from '@microsoft/fast-element';
import { ExampleChart } from './example-chart';

export const exampleChartTemplate = html<ExampleChart>`
  <template>
    <zero-g2plot-chart
      type="pie"
      :config=${(x) => x.chartConfiguration}
      :data=${(x) => x.chartData}
    ></zero-g2plot-chart>
  </template>
`;
