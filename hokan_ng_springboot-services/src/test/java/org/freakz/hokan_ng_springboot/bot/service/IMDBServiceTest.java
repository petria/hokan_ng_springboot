package org.freakz.hokan_ng_springboot.bot.service;

import org.freakz.hokan_ng_springboot.bot.models.IMDBData;
import org.freakz.hokan_ng_springboot.bot.service.imdb.IMDBServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Petri Airio on 18.11.2015.
 * -
 */
public class IMDBServiceTest {

  private static String SCENE_NAMES =
      "" +
          "Top_Gear.22x01.720p_HDTV_x264-FoV\n" +
          "The.Fast.Show.20th.Anniversary.Special.Part2.720p.HDTV.x264-BARGE\n" +
          "Tiger.Eyes.2012.LIMITED.DVDRip.x264-BiPOLAR\n" +
          "Fargo.S02E06.720p.HDTV.x264-FLEET\n" +
          "Fargo.S2E6.INTERNAL.720p.HDTV.x264-BATV\n" +
          "Hitman.Agent.47.2015.1080p.BluRay.x264-DRONES\n" +
          "Two.Men.in.Town.1973.720p.BluRay.x264-CiNEFiLE\n" +
          "Love.2015.720P.WEBRip.AAC.H264-Gaspar\n" +
          "The.Stanford.Prison.Experiment.2015.720p.WEB-DL.X264.AC3-EVO\n" +
          "Bound.To.Vengeance.2015.1080p.BluRay.x264-VeDeTT\n" +
          "Uncanny.2015.DVDRip.x264-NODLABS\n" +
          "90.Minutes.in.Heaven.2015.720p.WEB-DL.DD5.1.H.264-PLAYNOW\n" +
          "Two.Men.in.Town.1973.720p.BluRay.x264-CiNEFiLE\n" +
          "Love.2015.720P.WEBRip.AAC.H264-Gaspar\n" +
          "The.Stanford.Prison.Experiment.2015.720p.WEB-DL.X264.AC3-EVO\n" +
          "Bound.To.Vengeance.2015.1080p.BluRay.x264-VeDeTT\n" +
          "Uncanny.2015.DVDRip.x264-NODLABS\n" +
          "90.Minutes.in.Heaven.2015.720p.WEB-DL.DD5.1.H.264-PLAYNOW\n" +
          "The.33.2015.720p.WEB-DL.X264.AC3-EVO\n" +
          "Ted.2.2015.1080p.BluRay.x264-BLOW\n" +
          "The.Romantic.Englishwoman.1975.720p.BluRay.x264-FAPCAVE\n" +
          "Mississippi.Grind.2015.LIMITED.1080p.BluRay.x264-GECKOS";

  private IMDBServiceImpl service;

  @Before
  public void initTest() {
    service = new IMDBServiceImpl();
  }


  @Test
  public void testParseBogusName() {
    String bogusName = "Ffufufldsjkcfxfcxcas";
    String parsed = service.parseSceneMovieName(bogusName);
    Assert.assertEquals("n/a", parsed);
  }

  @Test
  public void testParseSceneNames() {
    String[] sceneNames = SCENE_NAMES.split("\n");
    for (String name : sceneNames) {
      String parsed = service.parseSceneMovieName(name);
      Assert.assertNotEquals("n/a", parsed);
    }
  }

  @Test
  public void testGetIMDBData() {
    String[] sceneNames = SCENE_NAMES.split("\n");
    String parsed = service.parseSceneMovieName(sceneNames[5]);
    IMDBData imdbData = service.findByTitle(parsed);
    Assert.assertEquals("tt2679042", imdbData.getOmdbVideoFull().getImdbID());

  }

}
