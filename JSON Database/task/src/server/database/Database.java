package server.database;

public interface Database<T> {
    Response set(Index<T> index, String value);

    Response get(Index<T> index);

    Response delete(Index<T> index);
}
