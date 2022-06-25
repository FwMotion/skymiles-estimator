import {bindable, BindingMode, observable, PLATFORM} from 'aurelia'
import {CreditCard} from 'skymiles-estimator-calculator'

export class SelectCreditcard {
  @bindable
  available = [...CreditCard.values()]

  @observable
  @bindable({mode: BindingMode.twoWay})
  selected

  @bindable
  change

  selectedChanged(...args) {
    if (typeof this.change === 'function') {
      PLATFORM.taskQueue.queueTask(
        () => this.change.call(null, ...args)
      )
    }
  }

  binding() {
    this.available.sort((a, b) => a.displayName.localeCompare(b.displayName))
  }
}
