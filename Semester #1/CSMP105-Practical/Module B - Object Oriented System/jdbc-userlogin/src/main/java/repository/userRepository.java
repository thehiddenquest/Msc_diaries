package repository;

import transfer_object.UserTO;

public class userRepository extends baseRepository<UserTO> {
	
	public userRepository(storageRepository<UserTO> storage) {
		super(storage);
	}

	@Override
	public boolean save(UserTO user) {
		boolean info = storage.store(user);
		return info;
	}

	@Override
	public UserTO load(UserTO obj) {
		UserTO user= storage.retrive(obj);
		return user;
	}

	@Override
	public boolean delete(UserTO user) {
		boolean info = storage.remove(user);
		return info;
	}

}
