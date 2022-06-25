import {bindable, BindingMode, observable, PLATFORM} from 'aurelia'

export class InputUsd {
  @observable
  @bindable({mode: BindingMode.twoWay})
  value = 0.00

  @bindable
  change

  valueChanged(...args) {
    if (typeof this.change === 'function') {
      PLATFORM.taskQueue.queueTask(
        () => this.change.call(null, ...args)
      )
    }
  }
}
