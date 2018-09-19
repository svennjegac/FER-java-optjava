package hr.fer.zemris.optjava.dz5.part1;

import java.util.Arrays;
import java.util.Random;

import hr.fer.zemris.optjava.dz5.algorithms.genetic.ICrossoverOperator;
import hr.fer.zemris.optjava.dz5.algorithms.genetic.ISolution;

public class TwoPointCrossover implements ICrossoverOperator<boolean[]> {

	Random rand = new Random(System.currentTimeMillis());
	
	@Override
	public ISolution<boolean[]> crossover(ISolution<boolean[]> solution1, ISolution<boolean[]> solution2) {
		boolean[] repr1 = Arrays.copyOf(solution1.getRepresentation(), solution1.getRepresentation().length);
		boolean[] repr2 = Arrays.copyOf(solution2.getRepresentation(), solution2.getRepresentation().length);
	
		int break1 = setBreak1(repr1, repr2);
		int break2 = setBreak2(repr1, repr2, break1 + 1);
		
		boolean[] childArray = createChildArray(repr1, repr2, break1, break2);
		
		return new BitVectorSolution(childArray);
	}

	private int setBreak1(boolean[] repr1, boolean[] repr2) {
		int breakIndex = -1;
		
		for (int i = 0; i < repr1.length / 3; i++) {
			if (repr1[i] == repr2[i]) {
				continue;
			}
			
			breakIndex = i;
		}
		
		if (breakIndex == -1 || breakIndex >= repr1.length - 2) {
			breakIndex = rand.nextInt(repr1.length - 2);
		}
		
		return breakIndex;
	}
	
	private int setBreak2(boolean[] repr1, boolean[] repr2, int from) {
		int breakIndex = -1;
		
		for (int i = from + repr1.length / 4; i < repr1.length; i++) {
			if (repr1[i] == repr2[i]) {
				continue;
			}
			
			breakIndex = i;
			break;
		}
		
		if (breakIndex == -1 || breakIndex >= repr1.length - 1) {
			breakIndex = from + rand.nextInt(repr1.length - 1 - from);
		}
		
		return breakIndex;
	}
	
	private boolean[] createChildArray(boolean[] repr1, boolean[] repr2, int break1, int break2) {
		boolean[] childArray = new boolean[repr1.length];
		
		for (int i = 0; i < repr1.length; i++) {
			if (i <= break1) {
				childArray[i] = repr1[i];
			} else if (i > break1 && i <= break2) {
				childArray[i] = repr2[i];
			} else {
				childArray[i] = repr1[i];
			}
		}
		
		return childArray;
	}
}