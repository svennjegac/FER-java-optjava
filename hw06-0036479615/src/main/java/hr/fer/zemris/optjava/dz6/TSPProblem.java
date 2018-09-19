package hr.fer.zemris.optjava.dz6;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.optjava.dz6.algorithms.ant.IProblem;
import hr.fer.zemris.optjava.dz6.algorithms.ant.ISolution;
import hr.fer.zemris.optjava.dz6.algorithms.ant.Point;

public class TSPProblem implements IProblem<int[]> {

	private List<Point> coordinates;
	private double[][] distances;
	private int candidates;
	private int[][] candidatesList;
	
	private Random rand;
	
	public TSPProblem(List<Point> coordinates, double[][] distances, int candidates) {
		this.coordinates = coordinates;
		this.distances = distances;
		this.candidates = candidates;
		rand = new Random(System.currentTimeMillis());
		
		initCandidatesList();
	}

	private void initCandidatesList() {
		candidatesList = new int[distances.length][candidates];
		
		
		for (int i = 0; i < distances.length; i++) {
			List<LocationDistance> locDists = new ArrayList<>();
			
			for (int j = 0; j < distances.length; j++) {
				locDists.add(new LocationDistance(j, distances[i][j]));
			}
			
			candidatesList[i] = locDists
									.stream()
									.sorted((ld1, ld2) -> Double.compare(ld1.distance, ld2.distance))
									.filter(ld -> ld.distance > 0)
									.limit(candidates)
									.mapToInt(ld -> ld.location)
									.toArray();
		}
	}

	@Override
	public double getFitness(ISolution<int[]> solution) {
		return -getValue(solution);
	}

	@Override
	public double getValue(ISolution<int[]> solution) {
		int[] order = solution.getRepresentation();
		double pathLength = 0;
		
		for (int i = 1; i < order.length; i++) {
			pathLength += distances[order[i]][order[i - 1]];
		}
		
		pathLength += distances[order[0]][order[order.length - 1]];
		return pathLength;
	}

	@Override
	public int[] getPossibleMoves(int location) {
		return candidatesList[location];
	}
	
	@Override
	public int[] getRemainingMoves(int[] solution) {
		List<Integer> moves = new ArrayList<>();
		
		for (int i = 0; i < distances.length; i++) {
			boolean visited = false;
			
			for (int j = 0; j < solution.length; j++) {
				if (solution[j] == i) {
					visited = true;
					break;
				}
			}
			
			if (visited) {
				continue;
			}
			
			moves.add(i);
		}
		
		return moves.stream().mapToInt(a -> a).toArray();
	}

	@Override
	public int getRandomStartPoint() {
		return rand.nextInt(distances.length);
	}

	@Override
	public int getSize() {
		return distances.length;
	}

	@Override
	public Point getPoint(int id) {
		return coordinates.stream().filter(point -> point.getId() == id).findAny().get();
	}

	@Override
	public ISolution<int[]> generateRandomSolution() {
		return Util.createInitialSolution(this);
	}

	@Override
	public Iterator<Point> iterator() {
		return coordinates.iterator();
	}
	
	@Override
	public void printSolution(ISolution<int[]> solution) {
		int[] order = solution.getRepresentation();
		double sum = 0;
		
		for (int i = 0; i < order.length - 1; i++) {
			sum += distances[order[i]][order[i + 1]];
			System.out.println((order[i] + 1) + " -> " + (order[i + 1] + 1) + " # " + distances[order[i]][order[i + 1]] + " ** " + sum);
		}
		
		sum += distances[order[order.length - 1]][order[0]];
		System.out.println((order[order.length - 1] + 1) + " -> " + (order[0] + 1) + " # " + distances[order[order.length - 1]][order[0]] + " ** " + sum);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(String.format("%7.1f", 0d) + " x ");
		
		for (int i = 0; i < distances.length; i++) {
			sb.append(String.format("%7.1f", (double) (i + 1)) + " x ");
		}
		
		sb.append(System.lineSeparator());
		
		for (int i = 0; i < distances.length; i++) {
			sb.append(String.format("%7.1f", (double) (i + 1)) + " x ");
			for (int j = 0; j < distances.length; j++) {
				sb.append(String.format("%7.1f", distances[i][j] ) + " | ");
			}
			
			sb.append(System.lineSeparator());
		}
		
		return sb.toString();
	}
	
	private static class LocationDistance {
		
		private int location;
		private double distance;
		
		public LocationDistance(int location, double distance) {
			this.location = location;
			this.distance = distance;
		}
	}
}
