import {render} from './helper'
import {SkymilesEstimator} from '../src/skymiles-estimator'

describe('skymiles-estimator', () => {
  it('should render itinerary', async () => {
    const node = (await render('<skymiles-estimator></skymiles-estimator>', SkymilesEstimator)).firstElementChild;
    const text = node.textContent;
    expect(text.trim()).toContain('Itinerary!');
  });
});
