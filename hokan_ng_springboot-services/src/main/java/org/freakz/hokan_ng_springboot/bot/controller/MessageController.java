package org.freakz.hokan_ng_springboot.bot.controller;

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

  @RequestMapping("/message")
  public String greeting(@RequestParam(value = "key", required = false) String key, Model model) {
    model.addAttribute("message", "tfufufufuf" + key);
    return "message";
  }


}
