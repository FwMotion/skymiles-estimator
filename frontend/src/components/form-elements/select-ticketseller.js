import {bindable, BindingMode, observable, PLATFORM} from 'aurelia'
import {TicketSeller} from 'skymiles-estimator-calculator'

export class SelectTicketseller {
  @bindable
  available = [...TicketSeller.values()]

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
