package org.freakz.hokan_ng_springboot.bot.controller;

import org.freakz.hokan_ng_springboot.bot.enums.LunchDay;
import org.freakz.hokan_ng_springboot.bot.enums.LunchPlace;
import org.freakz.hokan_ng_springboot.bot.models.LunchData;
import org.freakz.hokan_ng_springboot.bot.models.LunchMenu;
import org.freakz.hokan_ng_springboot.bot.service.lunch.LunchServiceImpl;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Petri Airio on 7.4.2016.
 * -
 */
@Controller
public class MessageController {

  @Autowired
  private LunchServiceImpl lunchService;


  @RequestMapping("/message")
  public String greeting(@RequestParam(value = "key", required = false) String key, Model model) {
    model.addAttribute("message", "tfufufufuf" + key);
    return "message";
  }

  @RequestMapping("/lunch")
  public String lunch(@RequestParam(value = "place", required = false) String placeKey, Model model) {
    LunchPlace place = LunchPlace.LOUNAS_INFO_ENERGIA_KEIDAS;
    if (placeKey != null) {
      place = LunchPlace.getLunchPlace(placeKey);
      if (place == null) {
        model.addAttribute("place", "Unknown place: " + placeKey);
        model.addAttribute("lunch", "");
        return "lunch";
      }
    }
    DateTime day = DateTime.now();
    LunchData lunchData = lunchService.getLunchForDay(place, day);
    LunchDay lunchDay = LunchDay.getFromDateTime(day);
    LunchMenu lunchMenu = lunchData.getMenu().get(lunchDay);

    model.addAttribute("place", place.getName());
    model.addAttribute("lunch", lunchMenu.getMenu());
    return "lunch";
  }



}
