package server.database;

import java.util.Objects;

public class Index<T> {
    private T index;

    public Index(T index) {
        this.index = index;
    }

    public T get() {
        return index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Index<?> index1 = (Index<?>) o;
        return Objects.equals(index, index1.index);
    }

    @Override
    public int hashCode() {
        return Objects.hash(index);
    }
}
