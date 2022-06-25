import {valueConverter} from 'aurelia'

@valueConverter('decimalPlaces')
export class FormatDecimalPlacesConverter {
  toView(value, decimalPlaces = 2) {
    if (typeof value === 'number') {
      value = value.toFixed(decimalPlaces)
    }
    return value
  }
  fromView(value) {
    if (typeof value === 'string' && !isNaN(parseFloat(value))) {
      value = parseFloat(value)
    }
    return value
  }
}
