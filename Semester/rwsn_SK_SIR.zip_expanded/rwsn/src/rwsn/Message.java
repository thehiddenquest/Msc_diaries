package rwsn;

public class Message<T> {
	int id;
	MessageTypes type;
	T data;
	Sensor obj;
	public Message(int id, MessageTypes type, T data) {
		this.id = id;
		this.type = type;
		this.data = data;
	}
	public Message(int id, MessageTypes type, T data, Sensor obj)
	{
		this.id = id;
		this.type = type;
		this.data = data;
		this.obj = obj;
	}
	
	@Override
	public String toString() {
		return "Msg:"+id+"->"+data;
	}
	
	
}
