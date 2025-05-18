import static java.sql.DriverManager.println;

public class App {
    public static void main(String[] args) throws Exception {
        // initializing all the different sudoku puzzles
        System.out.println("Sudoku 1: It should be consistent");
        start("C:\\Users\\pgalt\\OneDrive\\AIPT\\AIPT Assignments\\Assignment 2 Sudoku\\Java\\sudoku-csp\\Sudoku1.txt");
        System.out.println("Sudoku 2: It should be consistent");
        start("C:\\Users\\pgalt\\OneDrive\\AIPT\\AIPT Assignments\\Assignment 2 Sudoku\\Java\\sudoku-csp\\Sudoku2.txt");
        System.out.println("Sudoku 3: It should be consistent");
        start("C:\\Users\\pgalt\\OneDrive\\AIPT\\AIPT Assignments\\Assignment 2 Sudoku\\Java\\sudoku-csp\\Sudoku3.txt");
        System.out.println("Sudoku 4: It should be inconsistent");
        start("C:\\Users\\pgalt\\OneDrive\\AIPT\\AIPT Assignments\\Assignment 2 Sudoku\\Java\\sudoku-csp\\Sudoku4.txt");
        System.out.println("Sudoku 5: It should be consistent");
        start("C:\\Users\\pgalt\\OneDrive\\AIPT\\AIPT Assignments\\Assignment 2 Sudoku\\Java\\sudoku-csp\\Sudoku5.txt");
    }

    /**
     * Start AC-3 using the sudoku from the given filepath, and reports whether the sudoku could be solved or not, and how many steps the algorithm performed
     * 
     * @param filePath what does this do?
     */
    public static void start(String filePath){
        Game game1 = new Game(new Sudoku(filePath));
        game1.showSudoku();

        int without_heuristic = 1;
        int maximum_remainig_value = 2;
        int minimum_remaining_value = 3;
        int PFF = 4;
        int FF_MRV = 5;

        boolean resultWithoutHeuristic = game1.solve(without_heuristic);
        System.out.println("No Heuristics Result:" + (resultWithoutHeuristic ? "Solvable" : "Not Solvable"));

        Game game2 = new Game(new Sudoku(filePath)); // so that the board is not filled with the past game already
        boolean resultMaxRV = game2.solve(maximum_remainig_value);
        System.out.println("Maximum Remaining Values Result:" + (resultMaxRV ? "Solvable" : "Not Solvable"));

        Game game3 = new Game(new Sudoku(filePath)); // so that the board is not filled with the past game already
        boolean resultMiniRV = game3.solve(minimum_remaining_value);
        System.out.println("Minimum Remaining Value Result: " + (resultMiniRV ? "Solvable" : "Not Solvable"));

        Game game4 = new Game(new Sudoku(filePath)); // so that the board is not filled with the past game already
        boolean resultFF = game4.solve(PFF);
        System.out.println("Finalized fields Result: " + (resultFF ? "Solvable" : "Not Solvable"));

        Game game5 = new Game(new Sudoku(filePath)); // so that the board is not filled with the past game already
        boolean resultFFMRV = game5.solve(FF_MRV);
        System.out.println("Finalized fields + MRV Result: " + (resultFFMRV ? "Solvable" : "Not Solvable"));

        // checking the validity of the solutions
        if (game1.validSolution() && game2.validSolution() && game3.validSolution() && game4.validSolution() && game5.validSolution()){
            System.out.println("Actually solvable! Solution: " );
            game1.showSudoku();
        } else{
            System.out.println("Not solvable!");
        }

        // testing
        System.out.println("COMPLEXITY MEASURE");
        System.out.println("No heuristic: " + game1.getAddedArcs() + " added arcs");
        System.out.println("Maximum remaining values heuristic: " + game2.getAddedArcs() + " added arcs");
        System.out.println("Minimum remaining values heuristic: " + game3.getAddedArcs() + " added arcs");
        System.out.println("Finalized fields heuristic: " + game4.getAddedArcs() + " added arcs");
        System.out.println("Finalized fields + MRV heuristic: " + game5.getAddedArcs() + " added arcs");
        System.out.println("-----------------------------");
    }
}
