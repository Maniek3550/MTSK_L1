package dissimlab.broker;


/**
 * Description...
 * 
 * @author Dariusz Pierzchala
 *
 */
public abstract class BasicSubscriber implements ISubscriber {

	@Override
	public abstract void reflect(INotificationEvent event);
	@Override
	public boolean filter(IPublisher publisher, INotificationEvent event) {
		return true;	
	}
	public void subscribe(INotificationEvent event) {}
	public void unsubscribe(INotificationEvent event) {};
}
