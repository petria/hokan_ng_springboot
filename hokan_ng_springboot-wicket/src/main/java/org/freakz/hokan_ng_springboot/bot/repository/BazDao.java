package org.freakz.hokan_ng_springboot.bot.repository;

import org.springframework.stereotype.Repository;

@Repository
public class BazDao implements IBazDao {

	@Override
	public String fetchMessage() {
		return "Hello, Spring Boot + Wicket!";
	}

}
