package org.freakz.hokan_ng_springboot.bot.jpa.entity;

import java.io.Serializable;

/**
 * Created by Petri Airio on 23.2.2016.
 * -
 */
public enum UserFlags implements Serializable {

    ACTIVATED,
    ADMIN,
    CHANNEL_OP,
    IGNORE_ON_CHANNEL

}
