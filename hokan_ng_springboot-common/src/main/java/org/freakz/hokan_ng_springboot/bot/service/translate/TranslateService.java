package org.freakz.hokan_ng_springboot.bot.service.translate;

import java.util.List;

/**
 * PircBotUser: petria
 * Date: 12/14/13
 * Time: 9:06 AM
 *
 * @author Petri Airio <petri.j.airio@gmail.com>
 */
public interface TranslateService {

  List<String> translateFiEng(String keyword);

  List<String> translateEngFi(String keyword);

}
