package respository;

import transfer_object.BaseTO;

public interface StorageRepository {
	public void store(Object obj,String fileName);
	public Object getConnection(String filename);
}
