import java.io.*;
import java.util.*;

public class GameOfLife {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Welcome to Game of Life!");
		System.out.print("Enter number of rows: ");
		int row = sc.nextInt();
		System.out.print("Enter number of columns: ");
		int column = sc.nextInt();
		System.out.println("Enter your input in matrix format");
		char inputMatrix[][];
		
		inputMatrix = new char[row + 2][column + 2];

		// Adds extra outer '0' filled boundaries to the matrix
		for (int i = 0; i < row + 2; i++) {
			for (int j = 0; j < column + 2; j++) {
				if (i == 0 || j == 0 || i == row + 1 || j == column + 1) {
				inputMatrix[i][j] = 'O';	
				} else {
					inputMatrix[i][j] = sc.next().charAt(0);
				}
			}
		}

		System.out.println("Your matrix after one tick - ");

		trimTheBoundary(processMatrix(inputMatrix));
	}

	public static char[][] processMatrix(char[][] inputMatrix) {
		int row = inputMatrix.length;
		int column = inputMatrix[0].length;
		char newMatrix[][] = new char[row][column];
		
		// Processing the inputMatrix to newMatrix based on conditions specified
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				int count = numberOfAliveNeighbors(i, j, row, column, inputMatrix);
				char value = inputMatrix[i][j];
				if ((inputMatrix[i][j] == 'X' && count < 2) || (inputMatrix[i][j] == 'X' && count > 3)) {
					// Loneliness or Overcrowded
					value = 'O';
				}
				else if (inputMatrix[i][j] == 'O' && count == 3) {
					// Resurrection
					value = 'X';
				}
				newMatrix[i][j] = value;
			}
		}
		return newMatrix;
	}

	public static int numberOfAliveNeighbors(int x, int y, int row, int column, char[][] inputMatrix) {
		int count = 0;
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <=1; j++) {
				if (i == 0 && j == 0)
					continue;

				if ( 0 <= (x + i) && (x + i) < row && 0 <= (y + j) && (y + j) < column) {
					if (inputMatrix[x + i][y + j] == 'X')
						count++;
				}
			}
		}
		return count;

	}

	public static void trimTheBoundary(char[][] inputMatrix) {
		int row = inputMatrix.length;
		int column = inputMatrix[0].length;
		// O-top 1-right 2-bottom 3-left boundaries (assuming all to remove)
		boolean boundaries[] = {true, true, true, true};

		// Traverse outer boundaries
		String direction = "right";
		int i = 0;
		int j = 0;
		int count = (2 * (row + column)) - 4;

		// Finds which boundaries to remove from answer
		while(count > 0 ) {
			if (direction.compareTo("right") == 0) {
				if (inputMatrix[i][j] != 'O') 
					boundaries[0] = false;
				
				j++;
				if (j == column) {
					j = column - 1;
					i++;
					direction = "down";

				}
			}

			else if (direction.compareTo("down") == 0) {
				if (inputMatrix[i][j] != 'O')
					boundaries[1] = false;
				i++;
				if (i == row) {
					i = row - 1;
					j--;
					direction = "left";
				}

			}

			else if (direction.compareTo("left") == 0) {
				if (inputMatrix[i][j] != 'O')
					boundaries[2] = false;
				j--;
				if (j == -1) {
					j = 0;
					i--;
					direction = "up";
				}

			}

			else if (direction.compareTo("up") == 0) {
				if (inputMatrix[i][j] != 'O')
					boundaries[3] = false;
				i--;
				if (i == -1) {
					i = 0;
					direction = "right";
				}
			}

			count--;
		}
		

		// Display the trimmed matrix with trimmed boundaries
		for (i = 0; i < row; i++) {
			int flag = 0;
			for (j = 0; j < column; j++) {
				if (!((i == 0  && boundaries[0] == true) || (j == (column - 1) && boundaries[1] == true) || (i == (row - 1)  && boundaries[2] == true) || (j == 0  && boundaries[3] == true))) {
					flag = 1;
					System.out.print(inputMatrix[i][j] + " ");
				}
			}
			if (flag == 1) {
				System.out.println();
			}
		}
	}
}