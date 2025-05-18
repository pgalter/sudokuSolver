import java.util.PriorityQueue;
import java.util.*;


public class Game {
  private Sudoku sudoku;
  private int addedArcs;

  Game(Sudoku sudoku) {
    this.sudoku = sudoku;
  }

  public void showSudoku() {
    System.out.println(sudoku);
  }

  public int getAddedArcs() {
    return addedArcs;
  } // Complexity measure

  /**
   * Implementation of the AC-3 algorithm
   *
   * @return true if the constraints can be satisfied, else false
   */
  public boolean solve(int heuristic) {
    // TODO: implement AC-3

    Queue<Arc> queue; // list of Arcs, initially all Arcs
    Set<Arc> arcSet = new HashSet<>(); // Track arcs added to the queue

    // choose the heuristic
    if (heuristic == 1) { // no heuristic
      queue = new PriorityQueue<>();
    } else if (heuristic == 2) { // use the maximum remaining values heuristic
      queue = new PriorityQueue<>(new Comparator<Arc>() {
        @Override
        public int compare(Arc a, Arc b) {
          return Integer.compare(b.getY().getDomainSize(), a.getY().getDomainSize());
        }
      });

    } else if (heuristic == 3) { // use the minimum remaining values heuristic
      queue = new PriorityQueue<>(new Comparator<Arc>() {
        @Override
        public int compare(Arc a, Arc b) {
          return Integer.compare(a.getY().getDomainSize(), b.getY().getDomainSize());
        }
      });

    } else if (heuristic == 4) { // Priority to arcs with finalized fields
      queue = new PriorityQueue<>(new Comparator<Arc>() {
        @Override
        public int compare(Arc a, Arc b) {
          boolean isYFinalizedA = a.getY().getDomainSize() == 1; // True if Y in arc A is finalized
          boolean isYFinalizedB = b.getY().getDomainSize() == 1; //true if Y in arc B is finalized

          // Prioritize arcs with finalized Y
          if (isYFinalizedA && !isYFinalizedB) return -1; // Arc A has higher priority
          if (!isYFinalizedA && isYFinalizedB) return 1;  // Arc B has higher priority

          // If neither or both are finalized, fall back to default behavior (e.g., no priority)
          return 0;
        }
      });
    }else if (heuristic == 5) { // Finalized fields + MRV
        queue = new PriorityQueue<>(new Comparator<Arc>() {
          @Override
          public int compare(Arc a, Arc b) {
            boolean isYFinalizedA = a.getY().getDomainSize() == 1;
            boolean isYFinalizedB = b.getY().getDomainSize() == 1;

            if (isYFinalizedA && !isYFinalizedB) return -1; // Finalized field arcs first
            if (!isYFinalizedA && isYFinalizedB) return 1;

            return Integer.compare(a.getY().getDomainSize(), b.getY().getDomainSize()); // if both or neither are finalized, use MRV
          }
        });
    } else {
      throw new IllegalArgumentException("Invalid heuristic value. Use 0 for no heuristic, 1 for MaxRV or 2 for MRV.");
    }
    // Initialize all arcs (X, Y)
    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        Field X = sudoku.getBoard()[i][j]; //field from the grid
        if (X != null) {
          for (Field Y : X.getNeighbours()) {
            Arc arc = new Arc(X, Y);
            if (arcSet.add(arc)) { // Add to set and queue if not already present
              queue.add(arc);
            }
          }
        }
      }
    }
    while (!queue.isEmpty()) {
      Arc arc = queue.remove(); // Arc at the front of the queue and keep it in the variable
      arcSet.remove(arc);
      Field X = arc.getX();
      Field Y = arc.getY();

      if (revise(X, Y)) { // true if there is an inconsictency and it also changes the domain
        if (X.getDomain().isEmpty()) { // in the case that revise is true because there are no domains to compare
          return false; // an empty domain implies that there is no consistency (so far)
        }

        for (Field neighbour : X.getNeighbours()) { // if the domainX changes it adds the new arcs to the queue of X
          Arc newArc = new Arc(neighbour, X); // because the domainX changed and the neighbours must be rechecked
          if (arcSet.add(newArc)) { // Avoid duplicates
            queue.add(newArc);
            addedArcs++; // complexity measure
          }
        }
      }
    }
    return true; //meaning all arcs are consistent and thus the sudoku is as well
  }

  /**
   * Checks the validity of a sudoku solution
   *
   * @return true if the sudoku solution is correct
   */
  public boolean validSolution() {
    // TODO: implement validSolution function

    // Sets for tracking duplicates
    Set<Integer> rowSet = new HashSet<>();
    Set<Integer> colSet = new HashSet<>();
    Set<Integer> subgridSet = new HashSet<>();

    // Iterate through all rows, columns, and subgrids
    for (int i = 0; i < 9; i++) {
      rowSet.clear();
      colSet.clear();
      subgridSet.clear();

      for (int j = 0; j < 9; j++) {
        // Check rows
        Field rowField = sudoku.getBoard()[i][j];
        if (rowField.getValue() != 0 && !rowSet.add(rowField.getValue())) {
          return false;
        }

        // Check columns
        Field colField = sudoku.getBoard()[j][i];
        if (colField.getValue() != 0 && !colSet.add(colField.getValue())) {
          return false;
        }

        // Check subgrids
        int subgridRow = (i / 3) * 3 + j / 3;
        int subgridCol = (i % 3) * 3 + j % 3;
        Field subgridField = sudoku.getBoard()[subgridRow][subgridCol];
        if (subgridField.getValue() != 0 && !subgridSet.add(subgridField.getValue())) {
          return false;
        }
      }
    }
    return true;
  }

  private boolean revise(Field X, Field Y) {
    return X.removeFromDomain(Y.getValue()); //if the domainX has the value of Y (assuming it has a set value) then it will return false
  }
}