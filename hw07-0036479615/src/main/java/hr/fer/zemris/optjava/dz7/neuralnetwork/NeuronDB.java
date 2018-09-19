package hr.fer.zemris.optjava.dz7.neuralnetwork;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class NeuronDB implements Iterable<Neuron> {

	private Map<Id, Neuron> neurons;
	private int nextId;

	public NeuronDB() {
		this(Integer.MIN_VALUE);
	}
	
	public NeuronDB(int nextId) {
		neurons = new HashMap<>();
		this.nextId = nextId;
	}
	
	public Neuron createNeuron(double output, IActivationFunction activationFunction, ANN ann) {
		nextId++;
		Id id = new Id(nextId - 1);
		
		Neuron neuron = new Neuron(id, output, activationFunction, ann);
		neurons.put(id, neuron);
		return neuron;
	}
	
	public Neuron createNeuron(IActivationFunction activationFunction, ANN ann, boolean addBias) {
		nextId++;
		Id id = new Id(nextId - 1);
		
		Neuron neuron = new Neuron(id, activationFunction, ann, addBias);
		neurons.put(id, neuron);
		return neuron;
	}
	
	public Neuron getNeuronById(Id id) {
		return neurons.get(id);
	}
	
	public void putNeuron(Neuron neuron) {
		if (neuron.getId() == null) {
			System.out.println("");
		}
		
		neurons.put(neuron.getId(), neuron);
	}
	
	public boolean isPresent(Neuron neuron) {
		return neurons.containsValue(neuron);
	}
	
	public NeuronDB newInstanceWithSameCurrId() {
		return new NeuronDB(nextId);
	}

	@Override
	public Iterator<Neuron> iterator() {
		return neurons.entrySet().stream().map(entry -> entry.getValue()).iterator();
	}
}
