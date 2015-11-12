package org.freakz.hokan_ng_springboot.bot.service;

import org.freakz.hokan_ng_springboot.bot.models.TranslateData;
import org.freakz.hokan_ng_springboot.bot.service.translate.SanakirjaOrgTranslateService;
import org.freakz.hokan_ng_springboot.bot.service.translate.SanakirjaOrgTranslateServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by Petri Airio on 24.4.2015.
 *
 */
public class SanakirjaOrgTranslateServiceTest {

  private SanakirjaOrgTranslateService translateService;

  @Before
  public void beforeTest() {
    translateService = new SanakirjaOrgTranslateServiceImpl();
  }

  @Test
  public void testTranslate() {
    List<TranslateData> translateEngFi = translateService.translateEngFi("human");
    List<TranslateData> translateFiEng = translateService.translateFiEng("human");

    Assert.assertNotNull(translateEngFi);
    Assert.assertTrue(translateEngFi.size() > 0);
  }


}
