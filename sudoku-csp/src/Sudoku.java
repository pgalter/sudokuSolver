import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;


public class Sudoku {
  private Field[][] board;

  Sudoku(String filename) {
    this.board = readsudoku(filename);
  }

  @Override
  public String toString() {
    String output = "╔═══════╦═══════╦═══════╗\n";
		for(int i=0;i<9;i++){
      if(i == 3 || i == 6) {
		  	output += "╠═══════╬═══════╬═══════╣\n";
		  }
      output += "║ ";
		  for(int j=0;j<9;j++){
		   	if(j == 3 || j == 6) {
          output += "║ ";
		   	}
         output += board[i][j] + " ";
		  }

      output += "║\n";
	  }
    output += "╚═══════╩═══════╩═══════╝\n";
    return output;
  }

  /**
	 * Reads sudoku from file
	 * @param filename
	 * @return 2d int array of the sudoku
	 */
	public static Field[][] readsudoku(String filename) {
		assert filename != null && filename != "" : "Invalid filename";
		String line = "";
		Field[][] grid = new Field[9][9];
		try {
		FileInputStream inputStream = new FileInputStream(filename);
        Scanner scanner = new Scanner(inputStream);
        for(int i = 0; i < 9; i++) {
        	if(scanner.hasNext()) {
        		line = scanner.nextLine();
        		for(int j = 0; j < 9; j++) {
                  int numValue = Character.getNumericValue(line.charAt(j));
                  if(numValue == 0) {
                    grid[i][j] = new Field();
                  } else if (numValue != -1) {
                    grid[i][j] = new Field(numValue);
                        }
                }
        	}
        }
        scanner.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("error opening file: "+filename);
		}
    addNeighbours(grid);
		return grid;
	}

  /**
   * Adds a list of neighbours to each field, i.e., arcs to be satisfied
   * @param grid
   */
  private static void addNeighbours(Field[][] grid) {
    // TODO: for each field, add its neighbours

      for (int i = 0; i < 9; i++) { //initialize all the fields
          for (int j = 0; j < 9; j++) {
              Field currentField = grid[i][j];
              List<Field> neighbours = new ArrayList<>();

              // Adding columns and rows neighbours
              for (int n = 0; n < 9; n++) {
                  if (n !=j) { // Add i row and exclude the current field
                      neighbours.add(grid[i][n]);}
                  if (n !=i) { // Add j row and exclude the current field
                      neighbours.add(grid[n][j]);}
                  }

              // Adding square (3x3) neighbours
              int squareFirstRow = (i / 3) * 3; // The first column of the square of the gird
              int squareFirstColumn = (j / 3) * 3;
              for (int squareRow = squareFirstRow; squareRow < squareFirstRow + 3; squareRow++) {
                  for (int squareColumn = squareFirstColumn; squareColumn < squareFirstColumn + 3; squareColumn++) {
                      if ((squareRow != i || squareColumn != j) && !neighbours.contains(grid[squareRow][squareColumn])) { // to make sure that the number itself is not a neighbour
                          neighbours.add(grid[squareRow][squareColumn]);
                      }
                  }
              }
              currentField.setNeighbours(neighbours);
          }
      }
  }

  /**
	 * Generates fileformat output
	 */
	public String toFileString(){
        String output = "";
        for (int i = 0; i < board.length; i++) {
          for (int j = 0; j < board[0].length; j++) {
            output += board[i][j].getValue();
          }
          output += "\n";
        }
        return output;
	}

  public Field[][] getBoard(){
    return board;
  }
}

