import java.util.Objects;

// The Arc class represents a directional constraint between two fields in the grid.
public class Arc implements Comparable<Arc> {
    private final Field X; // The source field in the arc
    private final Field Y; // The destination field in the arc

    public Arc(Field X, Field Y) {
        this.X = X;
        this.Y = Y;
    }

    public Field getX() {
        return X;
    }

    public Field getY() {
        return Y;
    }

    @Override
    public int hashCode() {
        // Generates a unique hash for the arc using both fields.
        return Objects.hash(X, Y);
    }

    @Override
    public boolean equals(Object o) { // Returns true if both X and Y fields are identical.
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false; // Check type compatibility
        Arc arc = (Arc) o;
        return X == arc.X && Y == arc.Y; // Compare fields for identity
    }

    @Override
    public int compareTo(Arc other) { // Compares two Arc objects for ordering based on their fields' hash codes.
        int comparison = Integer.compare(this.X.hashCode(), other.X.hashCode()); // Compare source fields
        if (comparison == 0) {
            return Integer.compare(this.Y.hashCode(), other.Y.hashCode()); // Compare destination fields if sources are equal
        }
        return comparison;
    }
}

