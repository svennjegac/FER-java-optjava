package hr.fer.zemris.optjava.dz8.nn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class FeedForwardANN implements IANN {
	
	private static final double MIN_WEIGHT = -1d;
	private static final double MAX_WEIGHT = 1d;

	List<Layer> layers;
	private Random rand;
	int neuronCounter;
	
	public FeedForwardANN(int[] neuronsPerLayer, IActivationFunction[] activationFunctions) {
		layers = new ArrayList<>();
		rand = new Random(System.currentTimeMillis());
		neuronCounter = Integer.MIN_VALUE;
		
		initLayersAndNeurons(neuronsPerLayer, activationFunctions);
	}
	
	public FeedForwardANN(List<Layer> layers) {
		this.layers = layers;
		rand = new Random(System.currentTimeMillis());
	}
	
	private void initLayersAndNeurons(int[] neuronsPerLayer, IActivationFunction[] activationFunctions) {
		for (int i = 0; i < neuronsPerLayer.length; i++) {
			Layer layer = new Layer();
			
			for (int j = 0, neurons = neuronsPerLayer[i]; j < neurons; j++) {	
				Neuron neuron;
				
				if (i == 0) {
					neuron = new Neuron(new Id(neuronCounter), 0d, new IdentityFunction());
				} else {
					neuron = new Neuron(new Id(neuronCounter), 0d, activationFunctions[i - 1]);
					neuronCounter++;
					neuron.initBiasNeuron(new Id(neuronCounter));
				}
				
				layer.addNeuron(neuron);
				neuronCounter++;
			}
			
			layers.add(layer);
		}
	}
	
	@Override
	public double[] calculateOutput(double[] input) {
		initInputLayerOutputs(input);
		calculateOtherLayersOutput();
		return generateOutput();
	}

	void initInputLayerOutputs(double[] input) {
		Layer inputLayer = layers.get(0);
		
		for (int i = 0, size = inputLayer.size(); i < size; i++) {
			inputLayer.get(i).setOutput(input[i]);
		}
	}

	private void calculateOtherLayersOutput() {
		for (int i = 1, size = layers.size(); i < size; i++) {
			Layer receivingLayer = layers.get(i);
			
			for (int j = 0, neurons = receivingLayer.size(); j < neurons; j++) {
				receivingLayer.get(j).receiveSignal();
			}
		}
	}
	
	private double[] generateOutput() {
		Layer outputLayer = layers.get(layers.size() - 1);
		double[] output = new double[outputLayer.size()];
		
		for (int i = 0, size = output.length; i < size; i++) {
			output[i] = outputLayer.get(i).getOutput();
		}
		
		return output;
	}
	
	@Override
	public int getNumberOfInputs() {
		return layers.get(0).size();
	}
	
	@Override
	public int getNumberOfOutputs() {
		return layers.get(layers.size() - 1).size();
	}
	
	@Override
	public int getWeightsCount() {
		return getWeights().length;
	}
	
	@Override
	public double[] getWeights() {
		List<Double> weights = new ArrayList<>();
		
		for (Layer layer : layers) {
			for (Neuron neuron : layer) {
				for (Weight weight : neuron) {
					weights.add(weight.getValue());
				}
			}
		}
		
		return weights.stream().mapToDouble(d -> d.doubleValue()).toArray();
	}
	
	@Override
	public void setWeights(double[] weights) {
		int i = 0;
		
		for (Layer layer : layers) {
			for (Neuron neuron : layer) {
				for (Weight weight : neuron) {
					weight.setValue(weights[i]);
					i++;
				}
			}
		}
	}
	
	public void fullyConnectFeedforward() {
		fullyConnectFeedforward(() -> getRandomWeight());
	}
	
	public void fullyConnectFeedforward(IWeightGetter weightGetter) {
		for (int i = 1, size = layers.size(); i < size; i++) {
			Layer transmittingLayer = layers.get(i - 1);
			Layer receivingLayer = layers.get(i);
			
			for (int j = 0, rSize = receivingLayer.size(); j < rSize; j++) {
				receivingLayer.get(j).setBiasWeight(weightGetter.getWeight());
			}
			
			for (int j = 0, tSize = transmittingLayer.size(); j < tSize; j++) {
				for (int k = 0, rSize = receivingLayer.size(); k < rSize; k++) {
					Neuron.connectNeurons(
							transmittingLayer.get(j),
							receivingLayer.get(k),
							weightGetter.getWeight());
				}
			}
		}
	}
	
	double getRandomWeight() {
		return rand.nextDouble() * (MAX_WEIGHT - MIN_WEIGHT) + MIN_WEIGHT;
	}

	@Override
	public FeedForwardANN copy() {
		return new FeedForwardANN(copyLayers());
	}
	
	List<Layer> copyLayers() {
		Map<Id, Neuron> idNeuronMap = new HashMap<>();
		
		List<Layer> layers = new ArrayList<>();
		for (Layer layer : this.layers) {
			layers.add(layer.copy(idNeuronMap));
		}
		
		return layers;
	}
	
	@Override
	public void reset() {	
	}
	
	public interface IWeightGetter {
		public double getWeight();
	}
}