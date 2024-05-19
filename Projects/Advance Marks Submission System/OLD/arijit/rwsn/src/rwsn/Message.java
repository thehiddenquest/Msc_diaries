package rwsn;

public class Message <VALUE>{
	public VALUE value = null;
	public Message(VALUE value)
	{
		this.value = value;
	}
	
	public VALUE getValue() {
		return value;
	}
	
}
