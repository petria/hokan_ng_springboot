package org.freakz.hokan_ng_springboot.bot.ircengine;

import java.util.Date;

/**
 * User: petria
 * Date: 12/11/13
 * Time: 12:08 PM
 *
 * @author Petri Airio <petri.j.airio@gmail.com>
 */
public class CommandHistory {

	private long pid;
	private CommandRunnable runnable;
	private Object args;
	private long startTime;
	private long endTime;

	public CommandHistory(long pid, CommandRunnable runnable, Object args) {
		this.pid = pid;
		this.runnable = runnable;
		this.args = args;
		this.startTime = new Date().getTime();
	}

	public String toString() {
		String runtime = " (still running...)";
		if (endTime != 0) {
			runtime = String.format(" (runtime: %d ms)", (endTime - startTime));
		}
		return String.format("%4d %25s%s", pid, runnable.getClass().getSimpleName(), runtime);
	}

	public long getPid() {
		return pid;
	}

	public void setPid(long pid) {
		this.pid = pid;
	}

	public CommandRunnable getRunnable() {
		return runnable;
	}

	public void setRunnable(CommandRunnable runnable) {
		this.runnable = runnable;
	}

	public Object getArgs() {
		return args;
	}

	public void setArgs(Object args) {
		this.args = args;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
}
