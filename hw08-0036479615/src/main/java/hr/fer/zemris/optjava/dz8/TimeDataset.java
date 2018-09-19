package hr.fer.zemris.optjava.dz8;

import java.util.List;

import hr.fer.zemris.optjava.dz8.nn.IDataset;
import hr.fer.zemris.optjava.dz8.nn.LearningSample;

public class TimeDataset implements IDataset {

	private List<LearningSample> learningSamples;
	
	public TimeDataset(List<LearningSample> learningSamples) {
		this.learningSamples = learningSamples;
	}

	@Override
	public List<LearningSample> getLearningSamples() {
		return learningSamples;
	}

	@Override
	public int numberOfSamples() {
		return learningSamples.size();
	}
}
