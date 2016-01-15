package org.freakz.hokan_ng_springboot.bot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.freakz.hokan_ng_springboot.bot.dao.UserDao;
import org.freakz.hokan_ng_springboot.bot.model.User;

/**
 * 
 * UserService will be supplied from this class.
 * User effected operations will be done via this class
 * 
 * @author aakin
 * 
 */
@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserDao userDao;

	@Override
	public User loadUserByUsername(final String username) throws UsernameNotFoundException {
		return userDao.loadUserByUsername(username);
	}

}
