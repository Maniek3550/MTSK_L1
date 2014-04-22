package dissimlab.broker;

public interface IObserver {
	/**
	 * Comfortable to direct fast Notifier - Observer notification
	 */
	
	/**
	 * Method is called when an observable object should update self-info in Observer
	 */
	public void notification(INotifier notifier, INotificationEvent event);

}
