package server.database;

public class DatabaseIndex<T> {
    private T index;

    public DatabaseIndex(T index) {
        this.index = index;
    }

    public T get() {
        return index;
    }
}
