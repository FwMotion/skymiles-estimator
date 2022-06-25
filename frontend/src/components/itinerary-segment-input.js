import {bindable, BindingMode, observable} from 'aurelia'
import {getAvailableDestinations} from 'skymiles-estimator-calculator'

export class ItinerarySegmentInput {
  availableDestinations = []

  distance = 0.0

  @observable
  @bindable({mode: BindingMode.twoWay})
  segment

  @bindable({attribute: 'segment-changed'})
  triggerSegmentChanged() {
  }

  @bindable({attribute: 'destination-changed'})
  triggerDestinationChanged(newValue, oldValue) {
  }

  updateDisplayAttributes() {
    this.availableDestinations = getAvailableDestinations(this.segment.origin).sort()
    this.distance = this.segment.distance
  }

  segmentChanged() {
    this.updateDisplayAttributes()
  }

  originChanged() {
    this.updateDisplayAttributes()
    this.triggerSegmentChanged()
  }

  destinationChanged(newValue, oldValue) {
    this.updateDisplayAttributes()
    this.triggerDestinationChanged(newValue, oldValue)
    this.triggerSegmentChanged()
  }

  binding() {
    this.updateDisplayAttributes()
  }
}
