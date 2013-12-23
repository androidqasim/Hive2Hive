package org.hive2hive.core.process;

import org.hive2hive.core.exceptions.IllegalProcessStateException;
import org.hive2hive.core.process.listener.IProcessListener;

/**
 * This interface provides the default methods of a process which represents a use case.
 * 
 * @author Christian, Nico
 * 
 */
public interface IProcess extends Runnable {

	/**
	 * Starts the process, such that its state will be {@link ProcessState#RUNNING}. Only processes being in
	 * {@link ProcessState#INITIALIZING} state can be started.
	 */
	void start() throws IllegalProcessStateException;

	/**
	 * Pause the process, such that its state will be {@link ProcessState#PAUSED}. Only processes being in
	 * {@link ProcessState#RUNNING} state can be paused. The currently running {@link ProcessStep} will
	 * finish. The process will pause before the next {@link ProcessStep}.
	 */
	void pause() throws IllegalProcessStateException;

	/**
	 * Continues the process, such that its state will be {@link ProcessState#RUNNING}. Only processes being
	 * in {@link ProcessState#PAUSED} state can be continued.
	 */
	void continueProcess() throws IllegalProcessStateException;

	/**
	 * Stops the process and first issues a rollback, where the state changes to
	 * {@link ProcessState#ROLLBACKING}. After the rollback has completed, the process' state changes to
	 * {@link ProcessState#STOPPED}.
	 */
	void stop(String reason);

	/**
	 * Stops the process and first issues a rollback, where the state changes to
	 * {@link ProcessState#ROLLBACKING}. After the rollback has completed, the process' state changes to
	 * {@link ProcessState#STOPPED}.
	 */
	void stop(Exception exception);

	/**
	 * Terminates the process by signaling that no further steps have to be executed.</br>
	 * <b>Note:</b> This method does not have the same effect as {@link Process#stop(String)} since here no
	 * rollback will be initiated.
	 */
	void terminate() throws IllegalProcessStateException;

	/**
	 * Returns the per-{@link ProcessManager} unique PID of this process.
	 * 
	 * @return This process' PID.
	 */
	int getID();

	/**
	 * Returns the current {@link ProcessState} of this process.
	 * 
	 * @return This process' current state.
	 */
	ProcessState getState();

	/**
	 * Returns the current progress of this process.
	 * 
	 * @return This process' current progress.
	 */
	int getProgress();

	/**
	 * Add a listener to listen on process events (finishing, crashed, ...)
	 * 
	 * @param listener the listener to add
	 */
	void addListener(IProcessListener listener);

	/**
	 * Remove a listener from the process
	 * 
	 * @param listener the listener to remove
	 * @return if the removal was successful
	 */
	boolean removeListener(IProcessListener listener);

}
