package hr.fer.zemris.optjava.dz2;

public class FunctionFourNewton extends FunctionFour implements IHFunction {

	@Override
	public double[][] getHessianMatrix(double[] point) {
		double[][] matrix = new double[6][6];
		
		for (double[] row : problemRows) {
			matrix[0][0] += 2 * Math.pow(row[0], 2);
			matrix[0][1] += 2 * Math.pow(row[0], 4) * row[1];
			matrix[0][2] += 2 * Math.pow(Math.E, point[3] * row[2]) * row[0] * (1 + Math.cos(point[4] * row[3]));
			matrix[0][3] += 2 * point[2] * row[0] * row[2] * Math.pow(Math.E, point[3] * row[2]) * (1 + Math.cos(point[4] * row[3]));
			matrix[0][4] += -2 * point[2] * row[1] * row[3] * Math.pow(Math.E, point[3] * row[2]) * Math.sin(point[4] * row[3]);
			matrix[0][5] += 2 * row[0] * row[3] * Math.pow(row[4], 2);
			
			matrix[1][1] += 2 * Math.pow(row[0], 6) * Math.pow(row[1], 2);
			matrix[1][2] += 2 * Math.pow(row[0], 3) * row[1] * Math.pow(Math.E, point[3] * row[2]) * (1 + Math.cos(point[4] * row[3]));
			matrix[1][3] += 2 * point[2] * Math.pow(row[0], 3) * row[1] * row[2] * Math.pow(Math.E, point[3] * row[2]) * (1 + Math.cos(point[4] * row[3]));
			matrix[1][4] += -2 * point[2] * Math.pow(row[0], 3) * row[1] * row[3] * Math.pow(Math.E, point[3] * row[2]) * Math.sin(point[4] * row[3]);
			matrix[1][5] += 2 * Math.pow(row[0], 3) * row[1] * row[3] * Math.pow(row[4], 2);
			
			matrix[2][2] += 2 * Math.pow(Math.E, 2 * point[3] * row[2]) * Math.pow((1 + Math.cos(point[4] * row[3])), 2);
			matrix[2][3] += 2 * 3 * Math.pow(Math.E, point[3] * row[2]) * (1 + Math.cos(point[4] * row[3])) * (calculateValueInPointForExample(point, row) - row[row.length - 1])
					+ 2 * point[2] * row[2] * Math.pow(Math.E, 2 * point[3] * row[2]) * Math.pow((1 + Math.cos(point[4] * row[3])), 2);
			matrix[2][4] += -2 * row[3] * Math.pow(Math.E, point[3] * row[2]) * Math.sin(point[4] * row[3]) * (calculateValueInPointForExample(point, row) - row[row.length - 1])
					- 2 * point[2] * row[3] * Math.pow(Math.E, 2 * point[3] * row[2]) * Math.sin(point[4] * row[3]) * (1 + Math.cos(point[4] * row[3]));
			matrix[2][5] += 2 * row[3] * Math.pow(row[4], 2) * Math.pow(Math.E, point[3] * row[2]) * (1 + Math.cos(point[4] * row[3]));
			
			
			matrix[3][3] += 2 * point[2] * row[2] * row[2] * Math.pow(Math.E, point[3] * row[2]) * (1 + Math.cos(point[4] * row[3])) * (calculateValueInPointForExample(point, row) - row[row.length - 1])
					+ 2 * point[2] * point[2] * row[2] * row[2] * Math.pow(Math.E, 2 * point[3] * row[2]) * Math.pow((1 + Math.cos(point[4] * row[3])), 2);
			matrix[3][4] += -2 * point[2] * row[2] * row[3] * Math.pow(Math.E, point[3] * row[2]) * Math.sin(point[4] * row[3]) * (calculateValueInPointForExample(point, row) - row[row.length - 1])
					- 2 * point[2] * point[2] * row[2] * row[3] * Math.pow(Math.E, 2 * point[3] * row[2]) * Math.sin(point[4] * row[3]) * (1 + Math.cos(point[4] * row[3]));
			matrix[3][5] += 2 * point[2] * row[2] * row[3] * row[4] * row[4] * Math.pow(Math.E, point[3] * row[2]) * (1 + Math.cos(point[4] * row[3]));
			
			
			matrix[4][4] += 2 * point[2] * point[2] * row[3] * row[3] * Math.pow(Math.E, 2 * point[3] * row[2]) * Math.pow(Math.sin(point[4] * row[3]), 2) -
					2 * point[2] * row[3] * row[3] * Math.pow(Math.E, point[3] * row[2]) * Math.cos(point[4] * row[3]) * (calculateValueInPointForExample(point, row) - row[row.length - 1]);
			matrix[4][5] += -2 * point[2] * row[3] * row[3] * row[4] * row[4] * Math.pow(Math.E, point[3] * row[2]) * Math.sin(point[4] * row[3]);
			
			matrix[5][5] += 2 * row[3] * row[3] * Math.pow(row[4], 4);
		}
		
		matrix[1][0] = matrix[0][1];
		matrix[2][0] = matrix[0][2];
		matrix[3][0] = matrix[0][3];
		matrix[4][0] = matrix[0][4];
		matrix[5][0] = matrix[0][5];
		
		matrix[2][1] = matrix[1][2];
		matrix[3][1] = matrix[1][3];
		matrix[4][1] = matrix[1][4];
		matrix[5][1] = matrix[1][5];
		
		matrix[3][2] = matrix[2][3];
		matrix[4][2] = matrix[2][4];
		matrix[5][2] = matrix[2][5];
		
		matrix[4][3] = matrix[3][4];
		matrix[5][3] = matrix[3][5];
		
		matrix[5][4] = matrix[4][5];
		
		return matrix;
	}
}
