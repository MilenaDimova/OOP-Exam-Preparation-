package catHouse.repositories;


public interface Repository<T> {

    void buyToy(T toy);

    boolean removeToy(T toy);

    T findFirst(String type);
}
