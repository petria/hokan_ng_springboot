package org.freakz.hokan_ng_springboot.bot.models;

import java.io.Serializable;

/**
 * User: petria
 * Date: Jul 15, 2009
 * Time: 8:29:17 AM
 */
public class HoroHolder implements Serializable {

  public final static String[] HORO_NAMES =
      {"Oinas", "Härkä", "Kaksoset", "Rapu", "Leijona", "Neitsyt",
          "Vaaka", "Skorpioni", "Jousimies", "Kauris",
          "Vesimies", "Kalat"};

  public final static String[] HORO_DATES =
      {
          "21.3. - 19.4.", "20.4. - 20.5.", "21.5. - 20.6.", "21.6. - 22.7.",
          "23.7. - 22.8.", "23.8. - 22.9.", "23.9. - 23.10.", "24.10. - 22.11.",
          "23.11. - 21.12.", "22.12. - 19.1.", "20.1. - 19.2.", "20.2. - 20.3."
      };

  private int _horoscope;
  private String _horoscopeText;

  public HoroHolder(int horoscope, String horoscopeText) {
    _horoscope = horoscope;
    _horoscopeText = horoscopeText;
  }

  public int getHoroscope() {
    return _horoscope;
  }

  public String getHoroscopeText() {
    return _horoscopeText;
  }

  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append(HORO_NAMES[getHoroscope()]);
    sb.append(" (");
    sb.append(HORO_DATES[getHoroscope()]);
    sb.append("): ");
    sb.append(getHoroscopeText());
    return sb.toString();
  }

}
