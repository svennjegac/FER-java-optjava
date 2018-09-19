package hr.fer.zemris.optjava.dz8.nn;

import java.util.Map;

public class NeuronWeightPair {

	private Neuron neuron;
	private Weight weight;
	
	public NeuronWeightPair(Neuron neuron, Weight weight) {
		this.neuron = neuron;
		this.weight = weight;
	}
	
	public double getWeightedOutput() {
		return neuron.getOutput() * weight.getValue();
	}
	
	public Neuron getNeuron() {
		return neuron;
	}
	
	public Weight getWeight() {
		return weight;
	}
	
	public void setWeight(Weight weight) {
		this.weight = weight;
	}
	
	public NeuronWeightPair copy(Map<Id, Neuron> idNeuronMap) {
		return new NeuronWeightPair(neuron.copy(idNeuronMap), weight.copy());
	}
}
