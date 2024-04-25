package ems_package;

public interface StorageRepository {
	public void store(BaseEntity entiy, String filename);
	public Object getData(String filename);
}
