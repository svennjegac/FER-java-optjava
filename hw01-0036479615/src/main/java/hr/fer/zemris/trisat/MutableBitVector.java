package hr.fer.zemris.trisat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MutableBitVector extends BitVector {

	public MutableBitVector(boolean... bits) {
		this.bits = bits;
		this.numberOfBits = bits.length;
	}
	
	public MutableBitVector(int n) {
		this(new boolean[n]);
		
		randomlyFillBits(new Random(System.currentTimeMillis()), bits);
	}
	
	public void set(int index, boolean value) {
		bits[index] = value;
	}
	
	public static List<MutableBitVector> getOneDigitDiffNeighbours(MutableBitVector currentSolution) {
		List<MutableBitVector> neighbours = new ArrayList<>();
		neighbours.add(new MutableBitVector(Arrays.copyOf(currentSolution.bits, currentSolution.getSize())));
		
		for (int i = 0, size = currentSolution.getSize(); i < size; i++) {
			boolean[] neighbourBits = Arrays.copyOf(currentSolution.bits, currentSolution.getSize());
			neighbourBits[i] = neighbourBits[i] ^ true;
			
			neighbours.add(new MutableBitVector(neighbourBits));
		}
		
		return neighbours;
	}
}
