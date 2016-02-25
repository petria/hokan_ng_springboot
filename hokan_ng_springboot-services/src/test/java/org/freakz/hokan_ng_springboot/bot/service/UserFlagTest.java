package org.freakz.hokan_ng_springboot.bot.service;

import org.freakz.hokan_ng_springboot.bot.jpa.entity.UserFlag;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Petri Airio on 25.2.2016.
 * -
 */
public class UserFlagTest {

  @Test
  public void testFromStringToUserFlag() {
    String str = "ADMIN";
    UserFlag userFlag = UserFlag.getUserFlagFromString(str);
    Assert.assertEquals(UserFlag.ADMIN, userFlag);
  }

}
