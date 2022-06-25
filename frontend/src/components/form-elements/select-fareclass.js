import {bindable, BindingMode, observable, PLATFORM} from 'aurelia'
import {FareClass,getFareRules} from 'skymiles-estimator-calculator'

export class SelectFareclass {
  @observable
  @bindable
  marketedBy

  @observable
  @bindable({attribute: 'available'})
  availableSet = FareClass.values()

  available

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

  marketedByChanged() {
    this.inputsChanged()
  }

  availableSetChanged() {
    this.inputsChanged()
  }

  inputsChanged() {
    this.available = this.availableSet.filter((fareClass) => getFareRules(this.marketedBy, fareClass) !== null)

    if (this.displayForm === 'long') {
      this.available.sort((a, b) => getFareRules(this.marketedBy, a).displayName.localeCompare(getFareRules(this.marketedBy, b).displayName))
    } else {
      this.available.sort((a, b) => a.name$.localeCompare(b.name$))
    }
  }

  renderFareClassName(fareClass, displayForm) {
    switch (displayForm ?? this.displayForm) {
      case 'both':
        return `${fareClass.name$}: ${this.renderFareClassName(fareClass, 'long')}`
      case 'long':
        const fareRules = getFareRules(this.marketedBy, fareClass)
        if (fareRules !== null) {
          return fareRules.displayName
        }
        return 'Unknown'
      default:
      case 'short':
        return fareClass.name$
    }
  }

  binding() {
    this.inputsChanged()
  }

}
