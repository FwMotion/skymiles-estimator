import {bindable, BindingMode, observable, PLATFORM} from 'aurelia'
import {AirportCode} from 'skymiles-estimator-calculator'

export class SelectAirportcode {
  @bindable
  available = [...AirportCode.values()]

  @observable
  @bindable({mode: BindingMode.twoWay})
  selected

  @bindable
  displayForm = 'short'

  @bindable
  change

  selectedChanged(...args) {
    if (typeof this.change === 'function') {
      PLATFORM.taskQueue.queueTask(
        () => this.change.call(null, ...args)
      )
    }
  }

  renderAirportName(airportCode) {
    switch (this.displayForm) {
      case 'both':
        return `${airportCode.name$}: ${airportCode.displayName}`
      case 'long':
        return airportCode.displayName
      default:
      case 'short':
        return airportCode.name$
    }
  }

  binding() {
    if (this.displayForm === 'long') {
      this.available.sort((a, b) => a.displayName.localeCompare(b.displayName))
    } else {
      this.available.sort((a,b) => a.name$.localeCompare(b.name$))
    }
  }
}
