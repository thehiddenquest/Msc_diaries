package respository;

import transfer_object.BaseTO;


public class UserRepository implements BaseRepository {

	private StorageRepository repository;
	MySQLRepository msql = new MySQLRepository();

	public UserRepository(StorageRepository repository) {
		this.repository = repository;
	}
	@Override
	public StorageRepository getRepository() {
		return repository;
	}
	@Override
	public void save(BaseTO BD) {
		msql.store(BD);
	}
	public Object getuser(String username){
		Object obj =msql.restore(username);
		return obj;
	}

}
