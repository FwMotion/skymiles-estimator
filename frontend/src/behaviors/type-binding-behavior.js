import {bindingBehavior} from 'aurelia'

@bindingBehavior('type')
export class TypeBindingBehavior {
  bind(flags, scope, binding, ...valueTypes) {
    binding['pre-type-updateSource'] = binding.updateSource
    binding.updateSource = function (...args) {
      const hookedUpdateSource = binding['pre-type-updateSource']
      const newValue = args[0]

      for (const testValueType of valueTypes) {
        const newValueType = typeof newValue;

        if (newValueType === testValueType) {
          return hookedUpdateSource.apply(this, args)
        }

        if (newValueType !== 'string') {
          continue
        }

        switch (testValueType) {
          case 'number':
            let parsedNewValue = parseFloat(newValue)
            if (!isNaN(parsedNewValue)) {
              args.shift()
              args.unshift(parsedNewValue)
              return hookedUpdateSource.apply(this, args)
            }
            break
        }
        if (newValueType === testValueType) {
          return binding['pre-type-updateSource'](newValue, flags)
        }
      }
    }
  }

  unbind(flags, scope, binding) {
    binding.updateSource = binding['pre-type-updateSource']
    delete binding['pre-type-updateSource']
  }
}
