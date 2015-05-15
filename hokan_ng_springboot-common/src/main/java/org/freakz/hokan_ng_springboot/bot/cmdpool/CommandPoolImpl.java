package org.freakz.hokan_ng_springboot.bot.cmdpool;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.Property;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.PropertyName;
import org.freakz.hokan_ng_springboot.bot.jpa.service.PropertyService;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

  @Autowired
	private PropertyService propertyService;

	private ExecutorService executor = Executors.newCachedThreadPool();
	private List<CommandRunner> activeRunners = new ArrayList<>();
	private List<CommandHistory> commandHistory = new ArrayList<>();

	public CommandPoolImpl() {
	}

  private long getPid() {
    Property property = propertyService.findFirstByPropertyName(PropertyName.PROP_SYS_PID_COUNTER);
    if (property == null) {
      property = new Property(PropertyName.PROP_SYS_PID_COUNTER, "1", "");
    }
    long pid = Long.parseLong(property.getValue());
    property.setValue("" + pid + 1);
    propertyService.save(property);
    return pid;
  }

	@Override
	public void startRunnable(CommandRunnable runnable) {
		startRunnable(runnable, null);
	}

	@Override
	public void startRunnable(CommandRunnable runnable, Object args) {
    long pid = getPid();
		CommandRunner runner = new CommandRunner(pid, runnable, this, args);
		activeRunners.add(runner);
		this.executor.execute(runner);
	}

	@Override
	public void startSyncRunnable(CommandRunnable runnable, Object... args) {
    long pid = getPid();
		CommandRunner runner = new CommandRunner(pid, runnable, this, args);
		activeRunners.add(runner);
		runner.run();
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
