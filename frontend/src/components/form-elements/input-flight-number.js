import {bindable, BindingMode, observable, PLATFORM} from 'aurelia'
import {Airline} from 'skymiles-estimator-calculator'

export class InputFlightNumber {
  @bindable
  availableAirlines = [...Airline.values()]

  @observable
  @bindable({mode: BindingMode.twoWay})
  selectedAirline

  @observable
  @bindable({mode: BindingMode.twoWay})
  flightNumber

  @bindable
  change

  selectedAirlineChanged(newValue, oldValue) {
    if (typeof this.change === 'function') {
      PLATFORM.taskQueue.queueTask(
        () => this.change.call(null, `${newValue.displayCode}${this.flightNumber}`, `${oldValue.displayCode}${this.flightNumber}`)
      )
    }
  }

  flightNumberChanged(newValue, oldValue) {
    if (typeof this.change === 'function') {
      PLATFORM.taskQueue.queueTask(
        () => this.change.call(null, `${this.selectedAirline.displayCode}${newValue}`, `${this.selectedAirline.displayCode}${oldValue}`)
      )
    }
  }

  binding() {
    this.availableAirlines.sort((a, b) => a.displayCode.localeCompare(b.displayCode))
  }
}
