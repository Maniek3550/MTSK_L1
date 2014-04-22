package dissimlab.broker;

public interface INotifier extends ISender{
	/**
	 * Comfortable to direct fast Notifier - Observer notification
	 */

	// Obligatory is a List of registered observers
	//

	/**
	 * Method adds an observer to the notifier's list
	 */
	public void registerObserver(IObserver observer);

	/**
	 * Method deletes an observer from the notifier's list
	 */
	public void unregisterObserver(IObserver observer);

	/**
	 * Method notifies all observers from the notifier's list about even
	 */
	public void notifyObservers(INotifier notifier, INotificationEvent event);

}
