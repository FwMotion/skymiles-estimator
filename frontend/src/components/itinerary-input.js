import {bindable, BindingMode, observable} from 'aurelia'
import {Itinerary, ItinerarySegment} from 'skymiles-estimator-calculator'

const propertiesToCopyToItinerary = [
  'baseCost',
  'airlineFees',
  'governmentFeesAndTaxes'
]
const propertiesToCopyFromItinerary = [
  ...propertiesToCopyToItinerary,
  'totalCost',
  'totalDistance'
]

export class ItineraryInput {

  @observable
  @bindable({mode: BindingMode.twoWay})
  itinerary = new Itinerary()

  @bindable({attribute: 'itinerary-changed'})
  triggerItineraryChanged() {
  }

  @observable({callback: 'observedValueChanged'})
  baseCost

  @observable({callback: 'observedValueChanged'})
  airlineFees

  @observable({callback: 'observedValueChanged'})
  governmentFeesAndTaxes

  totalDistance
  totalCost

  updateDisplayAttributes() {
    const storageObject = {}
    for (const propName of propertiesToCopyFromItinerary) {
      storageObject[propName] = this.itinerary[propName]
    }
    for (const propName of propertiesToCopyFromItinerary) {
      this[propName] = storageObject[propName]
    }
  }

  itineraryChanged() {
    this.updateDisplayAttributes()
  }

  observedValueChanged() {
    const storageObject = {}
    for (const propName of propertiesToCopyToItinerary) {
      storageObject[propName] = this[propName]
    }
    for (const propName of propertiesToCopyToItinerary) {
      this.itinerary[propName] = storageObject[propName]
    }

    this.updateDisplayAttributes()
    this.triggerItineraryChanged()
  }

  otherValueChanged() {
    this.updateDisplayAttributes()
    this.triggerItineraryChanged()
  }

  addSegment() {
    const previousSegment = this.itinerary.segments[this.itinerary.segments.length - 1];
    const segment = new ItinerarySegment()

    segment.origin = previousSegment.destination
    segment.fareClass = previousSegment.fareClass
    segment.marketedBy = previousSegment.marketedBy
    segment.operatedBy = previousSegment.operatedBy
    segment.ticketedBy = previousSegment.ticketedBy

    this.itinerary.segments.push(segment)

    this.updateDisplayAttributes()
    this.triggerItineraryChanged()
  }

  removeSegment() {
    if (this.itinerary.segments.length < 2) {
      return
    }

    this.itinerary.segments.pop()

    this.updateDisplayAttributes()
    this.triggerItineraryChanged()
  }

  updateSegment(index) {
    // Trigger updating of a segment, so that available destinations render correctly
    const updating = this.itinerary.segments.splice(index, 1)
    this.itinerary.segments.splice(index, 0, ...updating)
  }

  updateDestination(index, newValue, oldValue) {
    if (index === this.itinerary.segments.length - 1) {
      return
    }

    const newDestination = this.itinerary.segments[index].destination

    const nextSegment = this.itinerary.segments[index + 1]

    if (nextSegment.origin === oldValue) {
      nextSegment.origin = newDestination
    }

    this.updateSegment(index + 1)
    this.updateDisplayAttributes()
    this.triggerItineraryChanged()
  }

  binding() {
    this.updateDisplayAttributes()
  }

}
