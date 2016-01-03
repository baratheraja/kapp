package in.org.kurukshetra.app16;

public class Matrix {
	private int column;
	private int row;
	private double[][] matrix = new double[4][4];
	private Matrix(int column, int row) {
		this.column = column;
		this.row = row;
		for (int i = 0; i < column; i ++) {
			for (int j = 0; j < row; j ++) {
				matrix[i][j] = 0;
			}
		}
	}
	private static final Matrix matrixMakeFromArray(int column, int row, double[][] data) {
		Matrix matrix = new Matrix(column, row);
		for (int i = 0; i < column; i ++) {
			for (int j = 0; j < row; j ++) {
				matrix.matrix[i][j] = data[i][j];
			}
		}
		return matrix;
	}
	private static final Matrix matrixMutiply(Matrix a, Matrix b) {
		Matrix matrix = new Matrix(a.column, b.row);
		for (int i = 0; i < a.column; i ++) {
			for (int j = 0; j < b.row; j ++) {
				for (int k = 0; k < a.row; k ++) {
					matrix.matrix[i][j] += a.matrix[i][k] * b.matrix[k][j];
				}
			}
		}
		return matrix;
	}
	
	public static final Point3D pointMakeRotation(Point3D point, Point3D direction, double angle) {
	   // Point3D resultPoint = increaseSpeed(point,direction,angle);
		return increaseSpeed(point,direction,angle);
	}

	public static final Point3D increaseSpeed(Point3D point,Point3D direction,double angle){
		if (angle == 0) {
			return point;
		}

		double[][] temp = {{point.x, point.y, point.z, 1}};
		Matrix result = matrixMakeFromArray(1, 4, temp);
		double temp1 = 0;
		if ((temp1 = direction.z * direction.z + direction.y * direction.y) != 0) {
			double cos1 = direction.z / Math.sqrt(temp1);
			double sin1 = direction.y / Math.sqrt(temp1);
			double[][] t1 = {{1, 0, 0, 0},{0, cos1, sin1, 0}, {0, -sin1, cos1, 0}, {0, 0, 0, 1}};
			Matrix m1 = matrixMakeFromArray(4, 4, t1);
			result = matrixMutiply(result, m1);
		}
		if ((temp1 = direction.x * direction.x + direction.y * direction.y + direction.z * direction.z) != 0) {
			double cos2 = Math.sqrt(direction.y * direction.y + direction.z * direction.z) / Math.sqrt(temp1);
			double sin2 = -direction.x / Math.sqrt(temp1);
			double[][] t2 = {{cos2, 0, -sin2, 0}, {0, 1, 0, 0}, {sin2, 0, cos2, 0}, {0, 0, 0, 1}};
			Matrix m2 = matrixMakeFromArray(4, 4, t2);
			result = matrixMutiply(result, m2);
		}

		double cos3 = Math.cos(angle);
		double sin3 = Math.sin(angle);
		double[][] t3 = {{cos3, sin3, 0, 0}, {-sin3, cos3, 0, 0}, {0, 0, 1, 0}, {0, 0, 0, 1}};
		Matrix m3 = matrixMakeFromArray(4, 4, t3);
		result = matrixMutiply(result, m3);

		if ((temp1 = direction.x * direction.x + direction.y * direction.y + direction.z * direction.z) != 0) {
			double cos2 = Math.sqrt(direction.y * direction.y + direction.z * direction.z) / Math.sqrt(temp1);
			double sin2 = -direction.x / Math.sqrt(temp1);
			double[][] t2 = {{cos2, 0, sin2, 0}, {0, 1, 0, 0}, {-sin2, 0, cos2, 0}, {0, 0, 0, 1}};
			Matrix m2 = matrixMakeFromArray(4, 4, t2);
			result = matrixMutiply(result, m2);
		}

		if ((temp1 = direction.z * direction.z + direction.y * direction.y) != 0) {
			double cos1 = direction.z / Math.sqrt(temp1);
			double sin1 = direction.y / Math.sqrt(temp1);
			double[][] t1 = {{1, 0, 0, 0},{0, cos1, -sin1, 0}, {0, sin1, cos1, 0}, {0, 0, 0, 1}};
			Matrix m1 = matrixMakeFromArray(4, 4, t1);
			result = matrixMutiply(result, m1);
		}

		Point3D resultPoint = new Point3D (result.matrix[0][0], result.matrix[0][1], result.matrix[0][2]);
		return resultPoint;
	}
}
