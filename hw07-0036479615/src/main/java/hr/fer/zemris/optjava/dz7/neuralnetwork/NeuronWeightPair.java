package hr.fer.zemris.optjava.dz7.neuralnetwork;

public class NeuronWeightPair {

	private Id neuronId;
	private Weight weight;
	
	public NeuronWeightPair(Id neuronId, Weight weight) {
		this.neuronId = neuronId;
		this.weight = weight;
	}
	
	public double getWeightValue() {
		return weight.getValue();
	}
	
	public Id getNeuronId() {
		return neuronId;
	}
	
	public Weight getWeight() {
		return weight;
	}
	
	public void setWeight(Weight weight) {
		this.weight = weight;
	}
	
	public NeuronWeightPair copy() {
		return new NeuronWeightPair(neuronId, weight.copy());
	}
}
