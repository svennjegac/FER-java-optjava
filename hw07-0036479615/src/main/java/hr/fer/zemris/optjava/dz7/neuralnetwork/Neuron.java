package hr.fer.zemris.optjava.dz7.neuralnetwork;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Neuron implements Iterable<Weight> {
	
	private Id id;
	private double output;
	private ANN ann;
	
	private IActivationFunction activationFunction;
	private List<NeuronWeightPair> neuronsIReceiveFrom;
	private List<NeuronWeightPair> neuronsITransmittTo;
	
	public Neuron(Id id, double output, IActivationFunction activationFunction, ANN ann) {
		this.id = id;
		this.output = output;
		neuronsIReceiveFrom = new ArrayList<>();
		neuronsITransmittTo = new ArrayList<>();
		this.activationFunction = activationFunction;
		this.ann = ann;
	}
	
	public Neuron(Id id, IActivationFunction activationFunction, ANN ann, boolean addBias) {
		this.id = id;
		this.activationFunction = activationFunction;
		neuronsIReceiveFrom = new ArrayList<>();
		neuronsITransmittTo = new ArrayList<>();
		this.ann = ann;
		
		if (addBias) {
			initBiasNeuron();
		}
	}
	
	public Neuron(Id id, double output, IActivationFunction activationFunction,
			List<NeuronWeightPair> neuronsIReceiveFrom, List<NeuronWeightPair> neuronsITransmittTo) {
		
		this.id = id;
		this.output = output;
		this.activationFunction = activationFunction;
		this.neuronsIReceiveFrom = neuronsIReceiveFrom;
		this.neuronsITransmittTo = neuronsITransmittTo;
	}

	private void initBiasNeuron() {
		Neuron biasNeuron = ann.getNeuronUtil().createNeuron(1d, activationFunction.copy(), ann);
		addToNeuronsIReceiveFrom(biasNeuron, new Weight(0d));
	}
	
	public void setBiasWeight(double weight) {
		neuronsIReceiveFrom.get(0).setWeight(new Weight(weight));
	}

	public double getOutput() {
		return output;
	}
	
	public void setOutput(double output) {
		this.output = output;
	}
	
	public void setAnn(ANN ann) {
		this.ann = ann;
	}
	
	public Id getId() {
		return id;
	}
	
	public List<NeuronWeightPair> getNeuronsIReceiveFrom() {
		return neuronsIReceiveFrom;
	}
	
	public static void connectNeurons(Neuron transmittingNeuron, Neuron receivingNeuron, double weight) {
		Weight w = new Weight(weight);
		
		transmittingNeuron.addToNeuronsITransmittTo(receivingNeuron, w);
		receivingNeuron.addToNeuronsIReceiveFrom(transmittingNeuron, w);
	}
	
	private void addToNeuronsIReceiveFrom(Neuron neuron, Weight w) {
		neuronsIReceiveFrom.add(new NeuronWeightPair(neuron.id, w));
	}
	
	private void addToNeuronsITransmittTo(Neuron neuron, Weight w) {
		neuronsITransmittTo.add(new NeuronWeightPair(neuron.id, w));
	}
	
	public void receiveSignal() {
		double sum = getWeightedSum();
		setOutput(activationFunction.valueAt(sum));
	}
	
	private double getWeightedSum() {
		double sum = 0;
		
		for (NeuronWeightPair neuronWeightPair : neuronsIReceiveFrom) {
			NeuronDB nu = ann.getNeuronUtil();
			Neuron n = nu.getNeuronById(neuronWeightPair.getNeuronId());
			sum += n.getOutput() * neuronWeightPair.getWeightValue();
		}
		
		return sum;
	}

	public Neuron copy() {
		List<NeuronWeightPair> recvNeurons = new ArrayList<>();
		List<NeuronWeightPair> transmittNeurons = new ArrayList<>();
		
		for (NeuronWeightPair neuronWeightPair : neuronsIReceiveFrom) {
			recvNeurons.add(neuronWeightPair.copy());
		}
		
		for (NeuronWeightPair neuronWeightPair : neuronsITransmittTo) {
			transmittNeurons.add(neuronWeightPair.copy());
		}
		
		return new Neuron(id, output, activationFunction, recvNeurons, recvNeurons);
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Neuron other = (Neuron) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
