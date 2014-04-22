package dissimlab.broker;

/**
 * Description...
 * 
 * @author Dariusz Pierzchala
 *
 */
public interface ISubscriber extends IReceiver{
	
	public void reflect(INotificationEvent event);
	public boolean filter(IPublisher publisher, INotificationEvent event);	
}
