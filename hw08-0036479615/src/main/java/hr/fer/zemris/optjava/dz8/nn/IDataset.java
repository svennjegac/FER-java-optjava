package hr.fer.zemris.optjava.dz8.nn;

import java.util.List;

public interface IDataset {

	public List<LearningSample> getLearningSamples();
	public int numberOfSamples();
}
