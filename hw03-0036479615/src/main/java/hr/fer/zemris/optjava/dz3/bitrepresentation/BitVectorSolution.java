package hr.fer.zemris.optjava.dz3.bitrepresentation;

import java.util.Arrays;
import java.util.Random;


public class BitVectorSolution {

	private byte[] bits;
	private int numberOfVariables;
	
	public BitVectorSolution(int bitsPerNumber, int numberOfVariables) {
		this.bits = new byte[bitsPerNumber * numberOfVariables];
		this.numberOfVariables = numberOfVariables;
	}
	
	public BitVectorSolution(byte[] bits, int numberOfVariables) {
		this.bits = bits;
		this.numberOfVariables = numberOfVariables;
	}
	
	public BitVectorSolution duplicate() {
		return new BitVectorSolution(Arrays.copyOf(bits, bits.length), numberOfVariables);
	}
	
	public void randomize(Random random, double[] deltas) {
		for (int i = 0; i < numberOfVariables; i++) {
			int zerosCounter = 0;
			
			for (int j = deltas.length - 1; j >= 1; j--) {
				if (bits[j] == 0) {
					zerosCounter++;
				} else {
					zerosCounter = 0;
				}
				
				if (zerosCounter == 6) {
					break;
				}
				
				randomlyChangeBit(random, deltas, i, j);
			}
			randomlyChangeBit(random, deltas, i, 0);
		}
	}
	
	private void randomlyChangeBit(Random random, double[] deltas, int i, int j) {
		if (random.nextDouble() < deltas[j]) {
			bits[i * deltas.length + j] = (byte) Math.abs(bits[i * deltas.length + j] - 1);
		}
	}
	
	public byte[] getBits() {
		return bits;
	}

	public int getNumberOfVariables() {
		return numberOfVariables;
	}
}
