package Repository;

public class UserRepository {
	MysqlRepository msql = new MysqlRepository();
	public void save(Object obj) {
		msql.store(obj);
	}
	public Object getuser(String username){
		Object obj =msql.restore(username);
		return obj;
	}
}
