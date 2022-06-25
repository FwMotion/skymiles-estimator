import {observable, PLATFORM} from 'aurelia';
import {
  Airline,
  calculateItineraryEarnings,
  CreditCard,
  FareClass,
  Itinerary,
  ItinerarySegment,
  MedallionStatus,
  TicketSeller
} from 'skymiles-estimator-calculator'

export class SkymilesEstimator {
  buildVersion = process.env.BUILD_VERSION ?? '0.0.0'
  buildDate = process.env.BUILD_TIMESTAMP ?? 'dev-build'

  resultsBox

  @observable
  itinerary

  @observable
  itinerarySerialized

  calculatedResult
  encodedWindowLocation = ''

  @observable
  showResults = false

  itineraryChanged() {
    this.itinerarySerialized = this.itinerary.serializedCode
    this.calculatedResult = calculateItineraryEarnings(this.itinerary)
  }

  itinerarySerializedChanged() {
    this.updateUrlFragment()
    this.encodedWindowLocation = encodeURIComponent(PLATFORM.window.location)
  }

  updateUrlFragment() {
    let urlFragment = this.itinerarySerialized;

    if (this.showResults) {
      urlFragment = 'results/' + urlFragment
    }

    const url = new URL(document.URL)
    url.hash = urlFragment
    PLATFORM.window.history.replaceState({}, "", url)
  }

  windowHashChangedListener
  windowHashChanged() {
    let hash = window.location.hash;

    if (hash.startsWith('#sample')) {
      const sampleNum = parseInt(hash.substring('#sample'.length))
      this.setSampleItinerary(sampleNum)
      this.showResults = true
      return
    }

    if (hash.startsWith('#results/')) {
      this.showResults = true
      hash = hash.substring('#results/'.length)
    }

    const itinerary = Itinerary.Companion.parseFromSerializedCode(hash)

    if (itinerary === null) {
      this.resetItinerary()
    } else {
      this.itinerary = itinerary
    }
  }

  showResultsChanged() {
    if (this.showResults === true) {
      PLATFORM.domReadQueue.queueTask(
        () => this.resultsBox.scrollIntoView({behavior: 'smooth', block: 'start'})
      )
    }
    this.updateUrlFragment()
  }

  resetItinerary() {
    this.itinerary = new Itinerary(
      MedallionStatus.NONE,
      [
        new ItinerarySegment(
          null,
          null,
          FareClass.X,
          Airline.DL,
          null,
          Airline.DL,
          Airline.DL
        )
      ],
      TicketSeller.DL,
      0.0,
      null,
      null,
      CreditCard.OTHER
    )
  }

  setSampleItinerary(sampleNumber) {
    let sampleItinerary
    switch(sampleNumber) {
      default:
      case 1:
        sampleItinerary = "#PKE1182/264.5/118.97DL_RESERVE/OMADTWDL4682DLDLT/DTWICNKE7274DLKEU/ICNSGNKE685KEKEU/SGNICNKE684KEKEU/ICNATLKE5035DLKEU/ATLOMAKE3628DLKEU"
        break
      case 2:
        sampleItinerary = "#PKE1650/223.7/119.17DL_RESERVE/MCIMSPKE3820DLKEW/MSPICNKE5034DLKEW/ICNBKKKE651KEKEK/BKKICNKE660KEKEK/ICNMSPKE5033DLKEW/MSPMCIDL2527DLKEW"
        break
      case 3:
        sampleItinerary = "#PAF8830/_/159.87DL_RESERVE/SEACDGAF_INTL367AF_INTLAF_INTLI/CDGHNDAF_INTL274AF_INTLAF_INTLI/HNDCDGAF_INTL293AF_INTLAF_INTLI/CDGSEAAF_INTL368AF_INTLAF_INTLI"
        break
    }

    this.itinerary = Itinerary.Companion.parseFromSerializedCode(sampleItinerary)
  }

  binding() {
    if (!!this.windowHashChangedListener) {
      this.unbinding()
    }
    this.windowHashChangedListener = () => this.windowHashChanged()
    window.addEventListener('hashchange', this.windowHashChangedListener)
    this.windowHashChanged()
  }

  unbinding() {
    window.removeEventListener('hashchange', this.windowHashChangedListener)
    this.windowHashChangedListener = null
  }

}
