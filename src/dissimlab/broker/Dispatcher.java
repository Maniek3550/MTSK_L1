package dissimlab.broker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description...
 * 
 * @author Dariusz Pierzchala
 *
 */
public class Dispatcher {
	// Map with a name of Event's class as a key and list of registered subscribers as value
	//
	private int id;
	private static int globalId; // Common counter for all Dispatchers 
	private Map<String, List<ISubscriber>> eventTypesAndSubscriptionsMap;
	
	public Dispatcher() {
		this.id = getGlobalId();
		this.eventTypesAndSubscriptionsMap = new HashMap<String, List<ISubscriber>>();
	}

	static int getGlobalId() {
		return globalId++;
	}

	//'eventClass' should be compatible with 'INotificationEvent' clinterfaceass
	@SuppressWarnings("unchecked")
	public void subscribe(ISubscriber subscriber, Class eventClass){
		List<ISubscriber> subscribersList;
		// Verification whether 'eventClass' is compatible with 'INotificationEvent' interface
		List<Class> allInterfaces = new ArrayList<Class>();
		allInterfaces.addAll(Arrays.asList(eventClass.getInterfaces()));
        allInterfaces.addAll(Arrays.asList(getSuperClassesInterfaces(eventClass)));
        allInterfaces= Arrays.asList(getSuperInterfaces((Class[]) allInterfaces.toArray(new Class[allInterfaces.size()])));
        boolean isNotificationEvent = false;
		for (Class clazz : allInterfaces) {
			if (clazz == INotificationEvent.class) {
				isNotificationEvent = true;
				break;
			}
		}
		if (isNotificationEvent) {
			if (eventTypesAndSubscriptionsMap.containsKey(eventClass.getSimpleName())) {
				subscribersList = eventTypesAndSubscriptionsMap.get(eventClass.getSimpleName());
			} else {
				subscribersList = new ArrayList<ISubscriber>();
			}
			subscribersList.add(subscriber);
			eventTypesAndSubscriptionsMap.put(eventClass.getSimpleName(),subscribersList);
		}
		//Is it necessary to return any boolean value?
	}
	
	@SuppressWarnings("unchecked")
	public void unsubscribe(ISubscriber subscriber, Class eventClass){
		// Verification whether 'eventClass' is compatible with 'INotificationEvent' interface is not necessary
		if (eventTypesAndSubscriptionsMap.containsKey(eventClass.getSimpleName())){
			List<ISubscriber> subscribersList = eventTypesAndSubscriptionsMap.get(eventClass.getSimpleName());
			subscribersList.remove(subscriber);
			eventTypesAndSubscriptionsMap.put(eventClass.getSimpleName(), subscribersList);
			//Is it necessary to return any boolean value?
		} 
	}
	
	public void publish(IPublisher publisher,INotificationEvent event) {
		if (eventTypesAndSubscriptionsMap.containsKey(event.getClass().getSimpleName())){
			for (ISubscriber subscriber : eventTypesAndSubscriptionsMap.get(event.getClass().getSimpleName())) {
				if (event.filter(subscriber)&&subscriber.filter(publisher, event))
					subscriber.reflect(event);
			}
		} 		
	}
	
	@SuppressWarnings("unchecked")
	private static Class[] getSuperClassesInterfaces(Class clazz) {
        List<Class> allInterfaces = new ArrayList <Class>();
        allInterfaces.addAll(
                Arrays.asList(clazz.getInterfaces()));
        if (clazz.getSuperclass()!=null){
        allInterfaces.addAll(
                Arrays.asList(getSuperClassesInterfaces(clazz.getSuperclass())));}
        return (Class[]) allInterfaces.toArray(new Class[allInterfaces.size()]);
    }	
	
	@SuppressWarnings("unchecked")
	private static Class[] getSuperInterfaces(Class[] interfaces) {
        List<Class> allInterfaces = new ArrayList <Class>();
        for (int i = 0; i < interfaces.length; i++) {
            allInterfaces.add(interfaces[i]);     
            allInterfaces.addAll(
                Arrays.asList(
                    getSuperInterfaces(interfaces[i].getInterfaces())));
        }        
        return (Class[]) allInterfaces.toArray(new Class[allInterfaces.size()]);
    }	
	
	/**
	 * Method creates short class'es name without prefix and '.'.
	 */
	@SuppressWarnings("unused")
	private static String getShortClassName(String name) {
		return name.substring(name.lastIndexOf(".") + 1);
	}
	
	public int getId() {
		return id;
	}
}
