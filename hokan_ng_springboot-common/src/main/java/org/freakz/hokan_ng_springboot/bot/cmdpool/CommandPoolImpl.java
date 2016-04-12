package org.freakz.hokan_ng_springboot.bot.cmdpool;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.CommandHistory;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.CommandStatus;
import org.freakz.hokan_ng_springboot.bot.jpa.service.CommandHistoryService;
import org.freakz.hokan_ng_springboot.bot.jpa.service.PropertyService;
import org.freakz.hokan_ng_springboot.bot.service.HokanModuleService;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * PircBotUser: petria
 * Date: 11/5/13
 * Time: 12:04 PM
 *
 * @author Petri Airio <petri.j.airio@gmail.com>
 */
@Component
@Slf4j
public class CommandPoolImpl implements CommandPool, DisposableBean {

  @Autowired
  private CommandHistoryService commandHistoryService;

  @Autowired
  private HokanModuleService hokanModuleService;

  @Autowired
  private PropertyService propertyService;

	private ExecutorService executor = Executors.newCachedThreadPool();
	private List<CommandRunner> activeRunners = new ArrayList<>();

	public CommandPoolImpl() {
	}

  private CommandHistory createCommandHistory(long pid, CommandRunnable runnable, String startedBy, Object args) {
    CommandHistory history = new CommandHistory();
    history.setHokanModule(hokanModuleService.getHokanModule().toString());
    history.setSessionId(hokanModuleService.getSessionId());
    history.setPid(pid);
    history.setStartTime(new Date());
    if (args == null) {
      history.setArgs("<none>");
    } else {
      history.setArgs(args.toString());
    }
    history.setRunnable(runnable.getClass().toString());
    history.setStatus(CommandStatus.RUNNING);
    history.setStartedBy(startedBy);
    history.setErrorException("");
    commandHistoryService.save(history);
    return history;
  }

  @Override
	public void startRunnable(CommandRunnable runnable, String startedBy) {
		startRunnable(runnable, startedBy, null);
	}

	@Override
	public void startRunnable(CommandRunnable runnable, String startedBy, Object args) {
    long pid = propertyService.getNextPid();
    CommandHistory history = createCommandHistory(pid, runnable, startedBy, args);
		CommandRunner runner = new CommandRunner(pid, runnable, this, args, history);
  	activeRunners.add(runner);
		this.executor.execute(runner);
	}

	@Override
  public void startSyncRunnable(CommandRunnable runnable, String startedBy, Object args) {
    long pid = propertyService.getNextPid();
    CommandHistory history = createCommandHistory(pid, runnable, startedBy, args);
		CommandRunner runner = new CommandRunner(pid, runnable, this, args, history);
		activeRunners.add(runner);
		runner.run();
	}

	@Override
	public void runnerFinished(CommandRunner runner, CommandHistory history, Exception error) {
    if (error != null) {
      history.setStatus(CommandStatus.ERROR);
      StringWriter sw = new StringWriter();
      error.printStackTrace(new PrintWriter(sw));
      history.setErrorException(sw.getBuffer().toString());
    } else {
      history.setStatus(CommandStatus.FINISHED);
      history.setErrorException("");
    }
    history.setEndTime(new Date());
    commandHistoryService.save(history);
		this.activeRunners.remove(runner);
	}

	@Override
	public List<CommandRunner> getActiveRunners() {
		return this.activeRunners;
	}

	@Override
	public void destroy() throws Exception {
		List<Runnable> runnableList = executor.shutdownNow();
		log.info("Runnable size: {}", runnableList.size());
	}

}
