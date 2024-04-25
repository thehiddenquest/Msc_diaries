package repository;

public abstract class baseRepository<T> {
	
	protected storageRepository<T> storage;
	
	public baseRepository(storageRepository<T> storage) {
		this.storage = storage;
	}
	
	public abstract boolean save(T obj);
	public abstract T load(T obj);
	public abstract boolean delete(T obj);
}
