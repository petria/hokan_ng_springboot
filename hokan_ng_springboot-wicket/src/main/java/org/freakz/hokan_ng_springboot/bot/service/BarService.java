package org.freakz.hokan_ng_springboot.bot.service;

import org.freakz.hokan_ng_springboot.bot.jpa.entity.User;
import org.freakz.hokan_ng_springboot.bot.jpa.repository.service.UserService;
import org.freakz.hokan_ng_springboot.bot.repository.IBazDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BarService implements IBarService {
	
	@Autowired
	private IBazDao dao;

    @Autowired
    private UserService userService;


	@Override
	public String fetchMessage() {
//		return "Hello, Spring Boot + Wicket!";
        List<User> user = userService.findAll();
		return dao.fetchMessage();
	}

}
