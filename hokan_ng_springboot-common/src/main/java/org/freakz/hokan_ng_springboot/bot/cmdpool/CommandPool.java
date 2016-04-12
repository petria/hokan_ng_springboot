package org.freakz.hokan_ng_springboot.bot.cmdpool;

import org.freakz.hokan_ng_springboot.bot.jpa.entity.CommandHistory;

import java.util.List;

/**
 * PircBotUser: petria
 * Date: 11/5/13
 * Time: 12:13 PM
 *
 * @author Petri Airio <petri.j.airio@gmail.com>
 */
public interface CommandPool {

	void startRunnable(CommandRunnable runnable, String startedBy, Object args);

	void startRunnable(CommandRunnable runnable, String startedBy);

	void startSyncRunnable(CommandRunnable runnable, String startedBy, Object args);

	void runnerFinished(CommandRunner runner, CommandHistory history, Exception error);

	List<CommandRunner> getActiveRunners();

}
