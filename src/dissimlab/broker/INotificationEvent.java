package dissimlab.broker;

public interface INotificationEvent {
	/**
	 * Method responsible for restricted/filtered sharing of information.
	 * Sender/publisher may define rules to limit the set of
	 * recipients/observers/subscribers. It returns TRUE when the information
	 * might be received by the 'subscriber'.
	 */
	public boolean filter(IReceiver receiver);

}
