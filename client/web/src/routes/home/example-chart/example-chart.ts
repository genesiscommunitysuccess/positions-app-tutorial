import { customElement, FASTElement, observable } from '@microsoft/fast-element';
import { exampleChartTemplate } from './example-chart.template';

@customElement({
  name: 'example-chart',
  template: exampleChartTemplate,
})
export class ExampleChart extends FASTElement {
  @observable chartConfiguration = {
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

  @observable chartData = [
    { type: 'Exam 1', value: 27 },
    { type: 'Exam 2', value: 25 },
    { type: 'Exam 3', value: 18 },
    { type: 'Exam 4', value: 15 },
    { type: 'Exam 5', value: 10 },
    { type: 'Exam 6', value: 13 },
  ];
}
