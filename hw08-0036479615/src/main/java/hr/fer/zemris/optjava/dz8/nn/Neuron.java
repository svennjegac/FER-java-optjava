package hr.fer.zemris.optjava.dz8.nn;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Neuron implements Iterable<Weight> {
	
	private double output;
	private Id id;
	
	private IActivationFunction activationFunction;
	private List<NeuronWeightPair> neuronsIReceiveFrom;
	private List<NeuronWeightPair> neuronsITransmittTo;
	
	public Neuron(Id id, double output, IActivationFunction activationFunction) {
		this.id = id;
		this.output = output;
		neuronsIReceiveFrom = new ArrayList<>();
		neuronsITransmittTo = new ArrayList<>();
		this.activationFunction = activationFunction;
	}
	
	public double getOutput() {
		return output;
	}
	
	public void setOutput(double output) {
		this.output = output;
	}
	
	public Id getId() {
		return id;
	}
	
	public List<NeuronWeightPair> getNeuronsIReceiveFrom() {
		return neuronsIReceiveFrom;
	}
	
	public void setNeuronsIReceiveFrom(List<NeuronWeightPair> neuronsIReceiveFrom) {
		this.neuronsIReceiveFrom = neuronsIReceiveFrom;
	}
	
	public List<NeuronWeightPair> getNeuronsITransmittTo() {
		return neuronsITransmittTo;
	}
	
	public void setNeuronsITransmittTo(List<NeuronWeightPair> neuronsITransmittTo) {
		this.neuronsITransmittTo = neuronsITransmittTo;
	}
	
	public void setBiasWeight(double weight) {
		neuronsIReceiveFrom.get(0).setWeight(new Weight(weight));
	}
	
	public void initBiasNeuron(Id id) {
		Neuron biasNeuron = new Neuron(id, 1d, activationFunction);
		addToNeuronsIReceiveFrom(biasNeuron, new Weight(0d));
	}
	
	public static void connectNeurons(Neuron transmittingNeuron, Neuron receivingNeuron, double weight) {
		Weight w = new Weight(weight);
		
		receivingNeuron.addToNeuronsIReceiveFrom(transmittingNeuron, w);
		transmittingNeuron.addToNeuronsITransmittTo(receivingNeuron, w);
	}
	
	private void addToNeuronsIReceiveFrom(Neuron neuron, Weight w) {
		neuronsIReceiveFrom.add(new NeuronWeightPair(neuron, w));
	}
	
	private void addToNeuronsITransmittTo(Neuron neuron, Weight w) {
		neuronsITransmittTo.add(new NeuronWeightPair(neuron, w));
	}
	
	public void receiveSignal() {
		double sum = getWeightedSum();
		setOutput(activationFunction.valueAt(sum));
	}
	
	private double getWeightedSum() {
		double sum = 0;
		
		for (NeuronWeightPair neuronWeightPair : neuronsIReceiveFrom) {
			sum += neuronWeightPair.getWeightedOutput();
		}
		
		return sum;
	}
	
	public Neuron copy(Map<Id, Neuron> idNeuronMap) {
		if (idNeuronMap.containsKey(id)) {
			return idNeuronMap.get(id);
		}
		
		Neuron copy = new Neuron(id, output, activationFunction);
		idNeuronMap.put(id, copy);
		
		List<NeuronWeightPair> neuronsIReceiveFrom = new ArrayList<>();
		for (NeuronWeightPair neuronWeightPair : this.neuronsIReceiveFrom) {
			neuronsIReceiveFrom.add(neuronWeightPair.copy(idNeuronMap));
		}
		
		List<NeuronWeightPair> neuronsITransmittTo = new ArrayList<>();
		for (NeuronWeightPair neuronWeightPair : this.neuronsITransmittTo) {
			neuronsITransmittTo.add(neuronWeightPair.copy(idNeuronMap));
		}
		
		copy.setNeuronsIReceiveFrom(neuronsIReceiveFrom);
		copy.setNeuronsITransmittTo(neuronsITransmittTo);
		
		return copy;
	}

	@Override
	public Iterator<Weight> iterator() {
		return new NeuronIterator();
	}
	
	private class NeuronIterator implements Iterator<Weight> {
		
		private int index;
		
		public NeuronIterator() {
			index = 0;
		}

		@Override
		public boolean hasNext() {
			return index < neuronsIReceiveFrom.size();
		}

		@Override
		public Weight next() {
			index++;
			return neuronsIReceiveFrom.get(index - 1).getWeight();
		}
	}
}