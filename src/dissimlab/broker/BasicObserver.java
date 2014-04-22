package dissimlab.broker;


public abstract class BasicObserver implements IObserver {

	public abstract void notification(INotifier notifier, INotificationEvent event);

}
