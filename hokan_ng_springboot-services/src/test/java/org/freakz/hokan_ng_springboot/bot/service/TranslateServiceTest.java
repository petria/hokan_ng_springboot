package org.freakz.hokan_ng_springboot.bot.service;

import org.freakz.hokan_ng_springboot.bot.HokanNgSpringBootServices;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by Petri Airio on 24.4.2015.
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = HokanNgSpringBootServices.class)
public class TranslateServiceTest {

  @Autowired
  private TranslateService translateService;

  @Test
  public void testTranslateEngFi() {
    List<String> words = translateService.translateEngFi("gay");
    Assert.assertNotNull(words);
    Assert.assertTrue(words.size() > 0);
  }


}
