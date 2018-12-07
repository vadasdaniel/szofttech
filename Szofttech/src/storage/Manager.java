package src.storage;

import java.util.List;

public interface Manager<T> {
    void readData();
    void delete(String id);
    T get(String id);
    void add(T content);
    List<T> list();
}
