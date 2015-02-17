package org.freakz.hokan_ng_sprintboot.io.ircengine;

import java.util.Date;

import org.freakz.hokan_ng_sprintboot.common.exception.HokanException;

import lombok.extern.slf4j.Slf4j;

/**
 * User: petria
 * Date: 11/5/13
 * Time: 12:09 PM
 *
 * @author Petri Airio <petri.j.airio@gmail.com>
 */
@Slf4j
public class CommandRunner implements Runnable {

	private long myPid;
	private CommandRunnable runnable;
	private CommandPoolImpl commandPool;
	private Object args;

	public CommandRunner(long myPid, CommandRunnable runnable, CommandPoolImpl commandPool, Object args) {
		this.myPid = myPid;
		this.runnable = runnable;
		this.commandPool = commandPool;
		this.args = args;
	}

	@Override
	public void run() {
		Thread.currentThread().setName("[" + myPid + "] CommandRunner: " + runnable);
		CommandHistory history = new CommandHistory(myPid, runnable, args);
		this.commandPool.addCommandHistory(history);
		try {
			this.runnable.handleRun(myPid, args);
		} catch (HokanException e) {
			log.error("CommandRunner error", e);
		}
		history.setEndTime(new Date().getTime());
		this.commandPool.runnerFinished(this);
	}

	@Override
	public String toString() {
		return String.format("%4d %25s", myPid, runnable.getClass().getSimpleName());
	}

}
