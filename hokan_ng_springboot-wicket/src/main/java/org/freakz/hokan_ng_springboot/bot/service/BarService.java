package org.freakz.hokan_ng_springboot.bot.service;

import org.freakz.hokan_ng_springboot.bot.repository.IBazDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BarService implements IBarService {
	
	@Autowired
	private IBazDao dao;

	@Override
	public String fetchMessage() {
//		return "Hello, Spring Boot + Wicket!";
		return dao.fetchMessage();
	}

}
