package respository;


import transfer_object.BaseTO;

public interface BaseRepository{
	public void save(BaseTO BD);
    public StorageRepository getRepository();
}
