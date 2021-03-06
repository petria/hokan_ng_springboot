package org.freakz.hokan_ng_springboot.bot.service.lunch.requesthandlers;

import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;

import lombok.extern.slf4j.Slf4j;

import org.freakz.hokan_ng_springboot.bot.enums.LunchDay;
import org.freakz.hokan_ng_springboot.bot.enums.LunchPlace;
import org.freakz.hokan_ng_springboot.bot.models.LunchData;
import org.freakz.hokan_ng_springboot.bot.models.LunchMenu;
import org.freakz.hokan_ng_springboot.bot.service.annotation.LunchPlaceHandler;
import org.freakz.hokan_ng_springboot.bot.service.lunch.LunchRequestHandler;
import org.freakz.hokan_ng_springboot.bot.util.StaticStrings;
import org.joda.time.DateTime;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

/**
 * Created by Petri Airio on 1.4.2016. -
 */
@Component
@Slf4j
public class VesilinnaLunchPlaceHandler implements LunchRequestHandler {

	@Override
	@LunchPlaceHandler(LunchPlace = LunchPlace.LOUNAS_INFO_VESILINNA)
	public void handleLunchPlace(LunchPlace lunchPlaceRequest, LunchData response, DateTime day) {
		response.setLunchPlace(lunchPlaceRequest);
		String url = lunchPlaceRequest.getUrl();
		Document doc;
		try {
			Authenticator.setDefault(new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("foo", "bar".toCharArray());
				}
			});
			doc = Jsoup.connect(url).timeout(0).userAgent(StaticStrings.HTTP_USER_AGENT).get();
		} catch (IOException e) {
			log.error("Could not fetch lunch from {}", url, e);
			return;
		}
		Elements elements = doc.select("p");
		// Elements elements = doc.getElementsByClass("wpb_wrapper");
		boolean startFound = false;
		for (Element element : elements) {
			String text = element.text();
			if (!startFound) {
				if (text.startsWith("PÄIVÄN LOUNAS")) {
					startFound = true;
				}

			} else {
				if (text.matches("Maanantai \\d.*")) {
					int idx = text.indexOf(" ");
					idx = text.indexOf(" ", idx + 1);
					String lunch = text.substring(idx + 1);
					LunchMenu lunchMenu = new LunchMenu(lunch);
					response.getMenu().put(LunchDay.MONDAY, lunchMenu);
				}
				if (text.matches("Tiistai \\d.*")) {
					int idx = text.indexOf(" ");
					idx = text.indexOf(" ", idx + 1);
					String lunch = text.substring(idx + 1);
					LunchMenu lunchMenu = new LunchMenu(lunch);
					response.getMenu().put(LunchDay.TUESDAY, lunchMenu);
				}
				if (text.matches("Keskiviikko \\d.*")) {
					int idx = text.indexOf(" ");
					idx = text.indexOf(" ", idx + 1);
					String lunch = text.substring(idx + 1);
					LunchMenu lunchMenu = new LunchMenu(lunch);
					response.getMenu().put(LunchDay.WEDNESDAY, lunchMenu);
				}
				if (text.matches("Torstai \\d.*")) {
					int idx = text.indexOf(" ");
					idx = text.indexOf(" ", idx + 1);
					String lunch = text.substring(idx + 1);
					LunchMenu lunchMenu = new LunchMenu(lunch);
					response.getMenu().put(LunchDay.THURSDAY, lunchMenu);
				}
				if (text.matches("Perjantai \\d.*")) {
					int idx = text.indexOf(" ");
					idx = text.indexOf(" ", idx + 1);
					String lunch = text.substring(idx + 1);
					LunchMenu lunchMenu = new LunchMenu(lunch);
					response.getMenu().put(LunchDay.FRIDAY, lunchMenu);
				}
			}
		}
		int foo = 0;

	}
}
