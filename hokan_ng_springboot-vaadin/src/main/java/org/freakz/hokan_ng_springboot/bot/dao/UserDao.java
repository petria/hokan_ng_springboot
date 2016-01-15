package org.freakz.hokan_ng_springboot.bot.dao;

import org.springframework.stereotype.Repository;
import org.freakz.hokan_ng_springboot.bot.model.Role;
import org.freakz.hokan_ng_springboot.bot.model.User;

import java.util.ArrayList;
import java.util.List;


/**
 * 
 * User database operations will be supplied from this class
 * 
 * @author aakin
 * 
 */
@Repository
public class UserDao {

	public User loadUserByUsername(final String username) {
		User user = new User();
		user.setFirstName("firstName");
		user.setLastName("lastName");
		user.setUsername("user");
		user.setPassword("1111");
		Role r = new Role();
		r.setName("ROLE_USER");
		List<Role> roles = new ArrayList<Role>();
		roles.add(r);
		r = new Role();
		r.setName("ROLE_ADMIN");
		roles.add(r);

		user.setAuthorities(roles);
		return user;
	}

}
