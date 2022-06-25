import {bindable, BindingMode, observable, PLATFORM} from 'aurelia'
import {MedallionStatus} from 'skymiles-estimator-calculator'

export class SelectMedallionstatus {
  @bindable
  available = [...MedallionStatus.values()]

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
}
