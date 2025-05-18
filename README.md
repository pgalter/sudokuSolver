# sudokuSolver
Sudoku Solver using Arc Consistency (AC-3) and Heuristics 

Implemented the AC-3 algorithm to solve Sudoku puzzles as a constraint satisfaction
problem, enforcing arc-consistency to reduce the search space.
The task at hand involves implementing a constraint satisfaction approach to solve Sudoku puzzles using the AC-3 (Arc Consistency 3) algorithm. My code can take a Sudoku puzzle as input, initialize its constraints, apply the algorithm to achieve arc-consistency, and evaluate the solution’s
correctness and runtime complexity. Moreover, the implementation includes different heuristics to optimize
the algorithm’s efficiency and measures their corresponding influence on the complexity.

The software works by modeling the Sudoku grid as a set of variables that represent each cell on the board.
These variables have different domains stored as sets, representing the possible values they can take given
the constraints and the already filled-in cells. The constraints consist of the typical Sudoku rules: every row,
column, and 3x3 grid must contain different values from 1 to 9. Each variable also has a set of neighbors
(other variables sharing the same row, column, or subgrid) that impose constraints on the values of their
corresponding domain. The program initializes the constraints and stores them as arcs, representing the
directed relationships betIen variables.

To ensure consistency, each possible arc (X, Y) is added to a queue so that the domain of X is revised to
remove values inconsistent with Y’s domain. If the domain of X is modified, arcs involving X are re-added
to the queue to be re-evaluated. The heuristics inform the way this queue is ordered and which arcs are
evaluated first. For example, a possible heuristic is the Maximum Remaining Values (MRV) heuristic, which
orders arcs with variables with the largest domains first to delay difficult decisions.
This propagation continues until there are no more inconsistencies betIen the arcs or until an empty domain is detected, which would mean that the puzzle is unsolvable. Because of this, the theoretical runtime complexity of AC-3 is O(c · d3), where c is the number of constraints and d is the maximum domain size.
Heuristics reduce the runtime complexity by decreasing the number of constraints that have to be revised
overall, so the complexity in the software is analyzed by counting how many arcs are added.

I implemented fMy different heuristics:
• Maximum Remaining Values (MRV Max): Prioritizes arcs connected to variables with the largest
remaining domain sizes. It aims to delay difficult decisions by involving heavily constrained variables
at a later stage, where the overall structure might be clearer

• Minimum Remaining Values (MRV): Prioritizes arcs connected to variables with the smallest
remaining domain sizes. It focuses first on variables closer to losing all possible values, where failure
is more imminent if consistency cannot be maintained. This allows the algorithm to identify inconsistencies sooner and prune the search space more aggressively.

• Priority to Finalised Fields: Gives priority to constraints connected to variables with singleton
domains—fully resolved variables with one possible value. This ensures their influence on neighboring
variables is applied immediately, leading to a faster reduction of domain spaces.

• Priority to Finalised Fields Combined with MRV: Combines the last two heuristics, prioritizing
arcs involving variables with singleton domains while considering unresolved variables with the smallest
remaining domains. This aims to leverage the advantages of both approaches for efficient propagation.

The following graphs showcase the runtime complexity results of each heuristic when solving the different
Sudoku puzzles. The Sudoku puzzle 4 was a very simple inconsistent and unsolvable Sudoku that was tested
to see how optimal are heuristics at figuring out if a puzzle is inconsistent. The y-axis represents the number
of arcs added.

![Screenshot 2025-05-18 155140](https://github.com/user-attachments/assets/1ae36333-d20d-4719-aef8-433e519e8dc8)
