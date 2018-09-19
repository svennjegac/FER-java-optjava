package hr.fer.zemris.optjava.dz8.nn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ElmanNN extends FeedForwardANN {

	private Layer contextLayer;
	
	public ElmanNN(int[] neuronsPerLayer, IActivationFunction[] activationFunctions) {
		super(neuronsPerLayer, activationFunctions);
		
		initContextLayer(neuronsPerLayer[1], activationFunctions[0]);
	}

	public ElmanNN(List<Layer> layers, Layer contextLayer) {
		super(layers);
		this.contextLayer = contextLayer;
	}
	
	private void initContextLayer(int neuronsPerLayer, IActivationFunction activationFunction) {
		contextLayer = new Layer();
		
		for (int i = 0; i < neuronsPerLayer; i++) {
			Neuron neuron = new Neuron(new Id(neuronCounter), 0d, new IdentityFunction());
			
			contextLayer.addNeuron(neuron);
			neuronCounter++;
		}
	}
	
	@Override
	void initInputLayerOutputs(double[] input) {
		super.initInputLayerOutputs(input);
		Layer firstHiddenLayer = layers.get(1);
		
		for (int i = 0; i < firstHiddenLayer.size(); i++) {
			contextLayer.get(i).setOutput(firstHiddenLayer.get(i).getOutput());
		}
	}
	
	@Override
	public void fullyConnectFeedforward(IWeightGetter weightGetter) {
		super.fullyConnectFeedforward(weightGetter);
		
		for (Neuron transmittingNeuron : contextLayer) {
			for (Neuron receivingNeuron : layers.get(1)) {
				Neuron.connectNeurons(
						transmittingNeuron,
						receivingNeuron,
						weightGetter.getWeight());
			}
		}
	}
	
	@Override
	public ElmanNN copy() {
		Map<Id, Neuron> idNeuronMap = new HashMap<>();
		
		List<Layer> layers = new ArrayList<>();
		for (Layer layer : this.layers) {
			layers.add(layer.copy(idNeuronMap));
		}
		
		Layer contextLayerCopy = contextLayer.copy(idNeuronMap);
		
		return new ElmanNN(layers, contextLayerCopy);
	}
	
	@Override
	public void reset() {
		for (Neuron neuron : layers.get(1)) {
			neuron.setOutput(0d);
		}
	}
}