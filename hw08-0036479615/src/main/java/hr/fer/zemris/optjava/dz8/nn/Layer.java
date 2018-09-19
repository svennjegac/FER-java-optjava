package hr.fer.zemris.optjava.dz8.nn;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Layer implements Iterable<Neuron>, Cloneable {

	private List<Neuron> neurons;
	
	public Layer() {
		this(new ArrayList<>());
	}
	
	public Layer(List<Neuron> neurons) {
		this.neurons = neurons;
	}
	
	public void addNeuron(Neuron neuron) {
		neurons.add(neuron);
	}
	
	public int size() {
		return neurons.size();
	}
	
	public Neuron get(int index) {
		return neurons.get(index);
	}

	public Layer copy(Map<Id, Neuron> idNeuronMap) {
		List<Neuron> neurons = new ArrayList<>();
		
		for (Neuron neuron : this.neurons) {
			neurons.add(neuron.copy(idNeuronMap));
		}
		
		return new Layer(neurons);
	}

	@Override
	public Iterator<Neuron> iterator() {
		return new LayerIterator();
	}
	
	private class LayerIterator implements Iterator<Neuron> {

		private int index;
		
		public LayerIterator() {
			index = 0;
		}
		
		@Override
		public boolean hasNext() {
			return index < neurons.size();
		}

		@Override
		public Neuron next() {
			index++;
			return neurons.get(index - 1);
		}
	}
}
