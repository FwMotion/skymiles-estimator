import Aurelia, { CustomElement } from 'aurelia'
export async function render(template, ...deps) {
  const wrapper = CustomElement.define({name: 'wrapper', template});
  const div = document.createElement('div');
  const au = Aurelia.register(deps).app({
    host: div,
    component: wrapper
  });
  await au.start();
  return div;
}
