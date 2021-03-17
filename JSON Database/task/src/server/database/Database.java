package server.database;

import java.util.Map;

public interface Database<T> {
    Map<String, String> set(DatabaseIndex<T> index, String value);

    Map<String, String> get(DatabaseIndex<T> index);

    Map<String, String> delete(DatabaseIndex<T> index);
}
