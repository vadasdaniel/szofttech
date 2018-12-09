package storage;

import java.util.List;

public interface Manager<T> {
    void readData();
    void delete(T content);
    void add(T content);
    T get(String id);
    List<T> list();
}
