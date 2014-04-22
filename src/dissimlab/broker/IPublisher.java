package dissimlab.broker;

/**
 * Description...
 * 
 * @author Dariusz Pierzchala
 *
 */
public interface IPublisher extends ISender{

	public void publish(INotificationEvent event);
}
