package ems_package;

public class EmployeeRepository implements BaseRepository{

	private StorageRepository repository;
    public EmployeeRepository(StorageRepository repository){
        this.repository=repository;
    }
	public StorageRepository getRepository(){
		return repository;
	}

     public void save(BaseEntity emp) {

        repository.store(emp,"employee.db");
        
    }
     public Object load() {
     	Object obj = repository.getData("employee.db");
     	return obj;
     }

}
