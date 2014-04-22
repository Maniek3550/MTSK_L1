package dissimlab.broker;

import java.util.ArrayList;
import java.util.List;

public abstract class BasicNotifier implements INotifier {
	// List of registered observers 
	//
	private final List<IObserver> observerList = new ArrayList<IObserver>();

	/**
	 * Method adds an observer to the notifier's list
	 */
	public void registerObserver(IObserver observer){
		if (!observerList.contains(observer)) {
			observerList.add(observer);
		}
	}

	/**
	 * Method deletes an observer from the notifier's list
	 */
	public void unregisterObserver(IObserver observer){
		observerList.remove(observer);
	}

	/**
	 * Method notifies all observers from the notifier's list about even
	 */
	public void notifyObservers(INotifier notifier, INotificationEvent event){
		for (IObserver observer : observerList) {
			observer.notification(notifier, event);
		}	
	}

}


