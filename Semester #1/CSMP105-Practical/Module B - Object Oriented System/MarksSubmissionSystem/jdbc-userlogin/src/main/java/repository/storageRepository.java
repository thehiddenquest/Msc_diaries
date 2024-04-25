package repository;

public abstract class storageRepository<T> {
	public abstract boolean store(T item);
	public abstract T retrive(T item);
	public abstract boolean remove(T item);
}
