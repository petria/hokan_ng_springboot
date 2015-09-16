package org.freakz.hokan_ng_springboot.bot.command.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Petri Airio on 15.9.2015.
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface HelpGroups {

  org.freakz.hokan_ng_springboot.bot.command.handlers.HelpGroup[] helpGroups();

}
