package org.freakz.hokan_ng_sprintboot.io.ircengine;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * User: petria
 * Date: 11/5/13
 * Time: 12:04 PM
 *
 * @author Petri Airio <petri.j.airio@gmail.com>
 */
@Component
@Slf4j
public class CommandPoolImpl implements CommandPool, DisposableBean {

	private static long pidCounter = 1;

	private ExecutorService executor = Executors.newCachedThreadPool();
	private List<CommandRunner> activeRunners = new ArrayList<>();
	private List<CommandHistory> commandHistory = new ArrayList<>();

	public CommandPoolImpl() {
	}

	@Override
	public void startRunnable(CommandRunnable runnable) {
		startRunnable(runnable, null);
	}

	@Override
	public void startRunnable(CommandRunnable runnable, Object args) {
		CommandRunner runner = new CommandRunner(pidCounter, runnable, this, args);
		activeRunners.add(runner);
		this.executor.execute(runner);
		pidCounter++;
	}

	@Override
	public void startSyncRunnable(CommandRunnable runnable, Object... args) {
		CommandRunner runner = new CommandRunner(pidCounter, runnable, this, args);
		activeRunners.add(runner);
		runner.run();
		pidCounter++;
	}

	@Override
	public void runnerFinished(CommandRunner runner) {
		this.activeRunners.remove(runner);
	}

	@Override
	public List<CommandRunner> getActiveRunners() {
		return this.activeRunners;
	}

	@Override
	public void destroy() throws Exception {
		List<Runnable> runnableList = executor.shutdownNow();
		log.info("Runnables size: {}", runnableList.size());
	}

	@Override
	public void addCommandHistory(CommandHistory history) {
		this.commandHistory.add(0, history);
	}

	@Override
	public List<CommandHistory> getCommandHistory() {
		return this.commandHistory;
	}
}
