package dissimlab.broker;

/**
 * Description...
 * 
 * @author Dariusz Pierzchala
 *
 */
public abstract class BasicNotificationEvent implements INotificationEvent{
	private ISender sender;
	
	public BasicNotificationEvent(ISender sender) {
		this.sender = sender;
	}

	@Override
	public abstract boolean filter(IReceiver receiver);

	public ISender getSender() {
		return sender;
	}

	public void setSender(ISender sender) {
		this.sender = sender;
	}

}
