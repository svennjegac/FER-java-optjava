package hr.fer.zemris.trisat;

import java.util.Arrays;
import java.util.Random;

public class BitVector {
	
	protected int numberOfBits;
	protected boolean[] bits;
	
	public BitVector(Random rand, int numberOfBits) {
		this.numberOfBits = numberOfBits;
		bits = new boolean[numberOfBits];
		
		randomlyFillBits(rand, bits);
	}
	
	public BitVector(boolean ... bits) {
		this.bits = bits;
		numberOfBits = bits.length;
	}
	
	public BitVector(int n) {
		this(new Random(System.currentTimeMillis()), n);
	}
	
	void randomlyFillBits(Random rand, boolean[] bits) {
		for (int i = 0; i < numberOfBits; i++) {
			bits[i] = rand.nextBoolean();
		}
	}
	
	public boolean get(int index) {
		return bits[index];
	}
	
	public int getSize() {
		return numberOfBits;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < bits.length; i++) {
			sb.append(bits[i] == true ? "1" : "0");
		}
		
		return sb.toString();
	}
	
	public MutableBitVector copy() {
		return new MutableBitVector(Arrays.copyOf(bits, bits.length));
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(bits);
		result = prime * result + numberOfBits;
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
		BitVector other = (BitVector) obj;
		if (!Arrays.equals(bits, other.bits))
			return false;
		if (numberOfBits != other.numberOfBits)
			return false;
		return true;
	}
}
