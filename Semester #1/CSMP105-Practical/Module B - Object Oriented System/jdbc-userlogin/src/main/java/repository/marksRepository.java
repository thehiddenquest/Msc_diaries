package repository;


import transfer_object.MarksTO;

public class marksRepository extends baseRepository<Object> {

	public marksRepository(storageRepository<Object> storage) {
		super(storage);
	}

	@Override
	public boolean save(Object marksList) {
	    boolean info = storage.store(marksList);
	    return info;
	}

	@Override
	public Object load(Object obj) {
		Object marks = (Object)storage.retrive(obj);
		return marks;
	}

	@Override
	public boolean delete(Object user) {
		boolean info = storage.remove(user);
		return info;
	}

}
