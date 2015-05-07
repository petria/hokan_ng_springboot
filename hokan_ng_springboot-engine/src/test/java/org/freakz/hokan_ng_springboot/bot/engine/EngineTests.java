package org.freakz.hokan_ng_springboot.bot.engine;

import org.freakz.hokan_ng_springboot.bot.HokanNgSpringBootEngine;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Petri Airio on 24.4.2015.
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = HokanNgSpringBootEngine.class)
public class EngineTests {

//  @Test
  public void testTranslateEngFi() {
    Assert.assertTrue(true);
  }

}
