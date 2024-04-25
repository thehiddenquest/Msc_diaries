package ems_package;

public class User_Repository implements BaseRepository {

	private StorageRepository repository;
    public User_Repository(StorageRepository repository){
        this.repository=repository;
    }
	public StorageRepository getRepository(){
		return repository;
	}

     public void save(BaseEntity u) {

        repository.store(u,"user.db");
        
    }
    public Object load() {
    	Object obj = repository.getData("user.db");
    	return obj;
    }

}
