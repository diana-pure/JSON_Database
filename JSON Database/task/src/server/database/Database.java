package server.database;

public interface Database {
    String set(Integer index, String value);

    String get(Integer index);

    String delete(Integer index);
}
