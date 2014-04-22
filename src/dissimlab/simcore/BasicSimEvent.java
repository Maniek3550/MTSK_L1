package dissimlab.simcore;

import dissimlab.simcore.SimParameters.SimControlStatus;
import dissimlab.simcore.SimParameters.SimEventStatus;

/**
 * Description...
 * 
 * @author Dariusz Pierzchala
 *
 */
public abstract class BasicSimEvent<TSimObj extends BasicSimObj, TParams> {
	/*
	 * Consider two subordinated classes: OneTimeSimStateChange, CyclicSimStateChange
	 */
	private double runTime;
	private double repetitionPeriod = 0.0;
	private int simPriority = SimParameters.DefaultSimPriority;
	SimEventStatus simStatus;
	private SimEventSemaphore simSemaphore = null;
	private TSimObj simObject = null;
	protected TParams transitionParams = null;  

	// Add immediately to common simEntity in the default context for the whole model (when stateChange is very general/common to model) - without any "in" parameters 
	// The same TO DO in the other cases
	public BasicSimEvent() throws SimControlException {
		this(0.0);
	}
	
	// Add to common simEntity in the default context for the whole model (when stateChange is very general/common to model) - without any "in" parameters 
	public BasicSimEvent(double delay) throws SimControlException {
		if (delay >=0.0) {
			this.simObject = (TSimObj)SimManager.getInstance().getCommonSimContext().getContextSimEntity();
			// register "entity-statechange-params" in context and model
			if (this.simObject!= null) {
				//possible problem with negative delay value
				this.simObject.createSimEvent((BasicSimEvent<BasicSimObj, Object>)this, delay);
				this.simStatus = SimEventStatus.WAITINGFORTRANSITION;
			}
			else
				throw new SimControlException("SimEntity does not exist");
				//System.err.println("SimEntity does not exist");
		}
		else
			throw new SimControlException("Time duration must not be negative");	
			//System.err.println("Time duration must not be negative");
	}	

	// Add immediately to common simEntity in the default context for the whole model (when stateChange is very general/common to model) 
	public BasicSimEvent(TParams params) throws SimControlException {
		this(0.0, params);
	}
	
	// Add to common simEntity in the default context for the whole model (when stateChange is very general/common to model) 
	public BasicSimEvent(double delay, TParams params) throws SimControlException {
		if (delay >=0.0) {
			this.simObject = (TSimObj)SimManager.getInstance().getCommonSimContext().getContextSimEntity();
			this.transitionParams = params;
			// register "entity-statechange-params" in context and model
			if (this.simObject!= null) {
				//possible problem with negative delay value
				this.simObject.createSimEvent((BasicSimEvent<BasicSimObj, Object>)this, delay);
				this.simStatus = SimEventStatus.WAITINGFORTRANSITION;
			}
			else
				throw new SimControlException("SimEntity does not exist");
		}
		else
			throw new SimControlException("Time duration must not be negative");	
	}
	
	// Add to common simEntity in the default context for the whole model (when stateChange is very general/common to model) 
	public BasicSimEvent(TParams params, int priority) throws SimControlException {
		this(0.0, params, priority);
	}
	
	// Add to common simEntity in the default context for the whole model (when stateChange is very general/common to model) 
	public BasicSimEvent(double delay, TParams params, int priority) throws SimControlException {
		if (delay >=0.0) {
			this.simObject = (TSimObj)SimManager.getInstance().getCommonSimContext().getContextSimEntity();
			this.simPriority = priority;
			this.transitionParams = params;
			// register "entity-statechange-params" in context and model
			if (this.simObject!= null) {
				//possible problem with negative delay value
				this.simObject.createSimEvent((BasicSimEvent<BasicSimObj, Object>)this, delay);
			}
			else
				throw new SimControlException("SimEntity does not exist");
		}
		else
			throw new SimControlException("Time duration must not be negative");	
	}
	
	//Add immediately
	public BasicSimEvent(TSimObj entity) throws SimControlException {
		this(entity, 0.0);
	}
	
	public BasicSimEvent(TSimObj entity, double delay) throws SimControlException {
		if (delay >=0.0) {
			this.simObject = entity;
			// register "entity-statechange-params" in context and model
			if (this.simObject!= null) {
				//possible problem with negative delay value
				this.simObject.createSimEvent((BasicSimEvent<BasicSimObj, Object>)this, delay);
			}
			else
				throw new SimControlException("SimEntity does not exist");
		}
		else
			throw new SimControlException("Time duration must not be negative");	
	}

	public BasicSimEvent(TSimObj entity, double delay, TParams params) throws SimControlException {
		if (delay >=0.0) {
			this.simObject = entity;
			this.transitionParams = params;
			// register "entity-statechange-params" in context and model
			if (this.simObject!= null) {
				//possible problem with negative delay value
				this.simObject.createSimEvent((BasicSimEvent<BasicSimObj, Object>)this, delay);
			}
			else
				throw new SimControlException("SimEntity does not exist");
		}
		else
			throw new SimControlException("Time duration must not be negative");	
	}

	public BasicSimEvent(TSimObj entity, double delay, int priority) throws SimControlException {
		if (delay >=0.0) {
			this.simObject = entity;
			this.simPriority = priority;
			// register "entity-statechange-params" in context and model
			if (this.simObject!= null) {
				//possible problem with negative delay value
				this.simObject.createSimEvent((BasicSimEvent<BasicSimObj, Object>)this, delay);
			}
			else
				throw new SimControlException("SimEntity does not exist");
		}
		else
			throw new SimControlException("Time duration must not be negative");	
	}

	public BasicSimEvent(TSimObj entity, double delay, TParams params, int priority) throws SimControlException {
		if (delay >=0.0) {
			this.simObject = entity;
			this.simPriority = priority;
			this.transitionParams = params;
			// register "entity-statechange-params" in context and model
			if (this.simObject!= null) {
				//possible problem with negative delay value
				this.simObject.createSimEvent((BasicSimEvent<BasicSimObj, Object>)this, delay);
			}
			else
				throw new SimControlException("SimEntity does not exist");
		}
		else
			throw new SimControlException("Time duration must not be negative");	
	}
	
	// Add to common simEntity in the default context for the whole model (when stateChange is very general/common to model) 
	public BasicSimEvent(SimEventSemaphore barrier, TParams params) throws SimControlException {
		if (barrier != null){
			this.simObject = (TSimObj)SimManager.getInstance().getCommonSimContext().getContextSimEntity();
			this.transitionParams = params;
			this.simSemaphore = barrier;
			// register "entity-statechange-params" in context and model
			if (this.simObject!= null) {
				this.simObject.createSimEvent((BasicSimEvent<BasicSimObj, Object>)this);
			}
			else
				throw new SimControlException("SimEntity does not exist");
		} 
		else 
			throw new SimControlException("SimConditionBarrier does not exist");		
	}
	
	public BasicSimEvent(TSimObj entity, SimEventSemaphore barrier) throws SimControlException {
		if (barrier != null){
			this.simObject = entity;
			this.simSemaphore = barrier;
			// register "entity-statechange-params" in context and model
			if (this.simObject!= null) {
				this.simObject.createSimEvent((BasicSimEvent<BasicSimObj, Object>)this);
			} 
			else
				throw new SimControlException("SimEntity does not exist");
		} 
		else 
			throw new SimControlException("SimConditionBarrier does not exist");				
	}
	
	public BasicSimEvent(TSimObj entity, SimEventSemaphore barrier, TParams params) throws SimControlException {
		if (barrier != null){
			this.simObject = entity;
			this.transitionParams = params;
			this.simSemaphore = barrier;
			// register "entity-statechange-params" in context and model
			if (this.simObject!= null) {
				this.simObject.createSimEvent((BasicSimEvent<BasicSimObj, Object>)this);
			} 
			else
				throw new SimControlException("SimEntity does not exist");
		} 
		else 
			throw new SimControlException("SimConditionBarrier does not exist");				
	}

	// Add to common simEntity in the default context for the whole model (when stateChange is very general/common to model) 
	public BasicSimEvent(TParams params, double period) throws SimControlException {
		if (period >=0.0) {
			this.simObject = (TSimObj)SimManager.getInstance().getCommonSimContext().getContextSimEntity();
			this.repetitionPeriod = period;
			this.transitionParams = params;
			// register "entity-statechange-params" in context and model
			if (this.simObject!= null) {
				//possible problem with negative delay value
				this.simObject.createSimEvent((BasicSimEvent<BasicSimObj, Object>)this, period);
			}
			else
				throw new SimControlException("SimEntity does not exist");
		}
		else
			throw new SimControlException("Time period must not be negative");	
	}
	
	public BasicSimEvent(TSimObj entity, TParams params, double period) throws SimControlException {
		if (period >=0.0) {
			this.simObject = entity;
			this.repetitionPeriod = period;
			this.transitionParams = params;
			// register "entity-statechange-params" in entity and model
			if (this.simObject!= null) {
				this.simObject.createSimEvent((BasicSimEvent<BasicSimObj, Object>)this, period);
			}
			else
				throw new SimControlException("SimEntity does not exist");
		}
		else
			throw new SimControlException("Time period must not be negative");	
	}

	// Add to common simEntity in the default context for the whole model (when stateChange is very general/common to model) 
	public BasicSimEvent(TParams params, double period, int priority) throws SimControlException {
		if (period >=0.0) {
			this.simObject = (TSimObj)SimManager.getInstance().getCommonSimContext().getContextSimEntity();
			this.repetitionPeriod = period;
			this.simPriority = priority;
			this.transitionParams = params;
			// register "entity-statechange-params" in context and model
			if (this.simObject!= null) {
				//possible problem with negative delay value
				this.simObject.createSimEvent((BasicSimEvent<BasicSimObj, Object>)this, period);
			}
			else
				throw new SimControlException("SimEntity does not exist");
		}
		else
			throw new SimControlException("Time period must not be negative");	
	}
	
	public BasicSimEvent(TSimObj entity, TParams params, double period, int priority) throws SimControlException {
		if (period >=0.0) {
			this.simObject = entity;
			this.repetitionPeriod = period;
			this.simPriority = priority;
			this.transitionParams = params;
			// register "entity-statechange-params" in context and model (-> calendar)
			if (this.simObject!= null) {
				this.simObject.createSimEvent((BasicSimEvent<BasicSimObj, Object>)this, period);
			}
			else
				throw new SimControlException("SimEntity does not exist");
		}
		else
			throw new SimControlException("Time period must not be negative");	
	}
	
	//Consider names: Run, Transform, Process, Alter, Realize
	public void service() throws SimControlException {		
		//Here are other additional actions...
		//Removed in the limited version
		if (getSimStatus() == SimEventStatus.INTERRUPTED){
			setSimStatus(SimEventStatus.PROCESSED);
			onInterruption();
			setSimStatus(SimEventStatus.DONE);
			if (repetitionPeriod >0.0) {
				//Step-driven mode: register repetitively cyclic "entity-statechange-params" in model (-> calendar)
				simObject.createSimEvent((BasicSimEvent<BasicSimObj, Object>)this, repetitionPeriod);
			} else {
				setSimStatus(SimEventStatus.DONE);
			}
		}
		else if (getSimStatus() == SimEventStatus.TERMINATED) {
			setSimStatus(SimEventStatus.PROCESSED);
			onTermination();
			setSimStatus(SimEventStatus.DONE);
		}
		else {
			setSimStatus(SimEventStatus.PROCESSED);
			stateChange(); // Before transition(...) proper transitionParams must be set.
			if (repetitionPeriod >0.0) {
				//Step-driven mode: register repetitively cyclic "entity-statechange-params" in model (-> calendar)
				simObject.createSimEvent((BasicSimEvent<BasicSimObj, Object>)this, repetitionPeriod);
			} else {
				setSimStatus(SimEventStatus.DONE);
			}
		}
		//Here are other additional actions...
		//Removed in the limited version
	}

	public boolean reschedule(double delay) throws SimControlException {
		//change runSimTime and sort calendar consequently
		if (simObject != null)
			return simObject.proceedRescheduleSimEvent((BasicSimEvent<BasicSimObj, Object>)this, delay);
		else
			throw new SimControlException("SimEntity does not exist");
	}

	public boolean terminate() throws SimControlException {
		return simObject.proceedTerminateSimEvent((BasicSimEvent<BasicSimObj, Object>)this);
	}

	public boolean interrupt() throws SimControlException {
		return simObject.proceedInterruptSimEvent((BasicSimEvent<BasicSimObj, Object>)this);
	}

	protected abstract void stateChange() throws SimControlException;
	
	protected abstract void onTermination() throws SimControlException;

	protected abstract void onInterruption() throws SimControlException;
/*
	public BasicSimStateChange<TEntity, TParams> createStateChange(
			BasicSimStateChangeCreator<TEntity, TParams> creator) {
		return owner.getContextRoot().createStateChange(
				BasicSimStateChangeCreator < TEntity, TParams > creator);
	}
*/
		
	public double simTime() {
		return simObject.simTime();
	}

	/**
	 *
	 *'0' means January and '11' = December | 
	 *Days of month are counted from '1' to ... 28,29,30 or 31 | 		
	 *'24' hours in a day
	 */
	public int simDate(SimParameters.SimDateField field) {
		return simObject.simDate(field);
	}
	
	public double getSimTimeStep() {
		return simObject.getSimTimeStep();
	}

	public void setSimTimeStep(double simTimeStep) {
		simObject.setSimTimeStep(simTimeStep);
	}

	public double getSimTimeScale() {
		return simObject.getSimTimeScale();
	}

	public void setSimTimeScale(double simTimeScale) {
		simObject.setSimTimeStep(simTimeScale);
	}
	
	//protected double getDispatcher() {
	//	return simEntity.getDispatcher();
	//}

	protected void stopSimulation(double duration) throws SimControlException {
		if (duration >=0.0) {
			SimControlEvent stopEvent = new SimControlEvent(duration, SimControlStatus.STOPSIMULATION, SimParameters.StopSimPriority);
		}
		else
			throw new SimControlException("Time duration must not be negative");	
			//System.err.println("Time duration must not be negative");
	}

	protected void stopSimulation() throws SimControlException {
		stopSimulation(0.0);
	}
	
	public double getRunTime() {
		return runTime;
	}

	void setRunTime(double runSimTime) {
		this.runTime = runSimTime;
	}

	public double getRepetitionPeriod() {
		return repetitionPeriod;
	}

	public void setRepetitionPeriod(double repetitionPeriod) {
		this.repetitionPeriod = repetitionPeriod;
	}

	public int getSimPriority() {
		return simPriority;
	}

	void setSimPriority(int priority) {
		this.simPriority = priority;
	}

	public SimEventStatus getSimStatus() {
		return simStatus;
	}

	void setSimStatus(SimEventStatus simStatus) {
		this.simStatus = simStatus;
	}

	public SimEventSemaphore getSimEventSemaphore() {
		return simSemaphore;
	}

	void setSimEventSemaphore(SimEventSemaphore simSemaphore) {
		this.simSemaphore = simSemaphore;
	}

	public TSimObj getSimObj() {
		return simObject;
	}

	void setSimObj(TSimObj owner) {
		this.simObject = owner;
	}
	
	@Override
	public String toString()
	{
		return this.getClass().getName().toString();
	}
}