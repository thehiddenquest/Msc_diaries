package ems_package;

public interface BaseRepository {
	public void save(BaseEntity entiy);
    public StorageRepository getRepository();
}
