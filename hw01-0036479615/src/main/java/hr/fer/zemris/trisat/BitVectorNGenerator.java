package hr.fer.zemris.trisat;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class BitVectorNGenerator implements Iterable<MutableBitVector> {
	
	private BitVector assignment;
	
	public BitVectorNGenerator(BitVector assignment) {
		this.assignment = assignment;
	}

	@Override
	public Iterator<MutableBitVector> iterator() {
		return new VectorIterator();
	}
	
	public MutableBitVector[] createNeighbourhood() {
		List<MutableBitVector> neighbours = MutableBitVector.getOneDigitDiffNeighbours(
				new MutableBitVector(Arrays.copyOf(assignment.bits, assignment.getSize())));
		
		return neighbours.toArray(new MutableBitVector[neighbours.size()]);
	}
	
	public class VectorIterator implements Iterator<MutableBitVector> {

		private int bitChangingIndex;
		private MutableBitVector[] neighbours;
		
		public VectorIterator() {
			bitChangingIndex = 0;
			neighbours = createNeighbourhood();
		}
		
		@Override
		public boolean hasNext() {
			if (bitChangingIndex < neighbours.length) {
				return true;
			}
			
			return false;
		}

		@Override
		public MutableBitVector next() {
			if (!hasNext()) {
				throw new NoSuchElementException("No more elements.");
			}
			
			bitChangingIndex++;
			
			return neighbours[bitChangingIndex - 1];
		}
	}
}
