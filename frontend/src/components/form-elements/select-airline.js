import {bindable, BindingMode, observable, PLATFORM} from 'aurelia'
import {Airline} from 'skymiles-estimator-calculator'

export class SelectAirline {
  @bindable
  available = [...Airline.values()]

  @observable
  @bindable({mode: BindingMode.twoWay})
  selected

  @bindable
  displayForm = 'name'

  @bindable
  change

  selectedChanged(...args) {
    if (typeof this.change === 'function') {
      PLATFORM.taskQueue.queueTask(
        () => this.change.call(null, ...args)
      )
    }
  }

  renderAirlineName(airline) {
    switch (this.displayForm) {
      case 'both':
        return `${airline.displayCode}: ${airline.displayName}`
      case 'long':
        return airline.displayName
      default:
      case 'short':
        return airline.selectionCode
    }
  }

  binding() {
    switch (this.displayForm) {
      case 'both':
        this.available.sort((a, b) => a.displayCode.localeCompare(b.displayCode))
        break
      case 'long':
        this.available.sort((a, b) => a.displayName.localeCompare(b.displayName))
        break
      default:
      case 'short':
        this.available.sort((a, b) => a.selectionCode.localeCompare(b.selectionCode))
    }
  }

}
