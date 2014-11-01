package com.app.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.app.dao.UserDAO;
import com.app.model.Role;

public class UserService implements UserDetailsService {

	// get user from the database, via Hibernate
	@Autowired
	private UserDAO userDao;

	@Transactional(readOnly = true)
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		com.app.model.User user = userDao.findUserByName(username);
		List<GrantedAuthority> authorities = buildUserAuthority(user
				.getUserRole());
		return buildUserForAuthentication(user, authorities);
	}

	// Converts com.app.model.User user to
	// org.springframework.security.core.userdetails.User
	private User buildUserForAuthentication(com.app.model.User user,
			List<GrantedAuthority> authorities) {
		return new User(user.getName(), user.getPassword(), user.isEnabled(),
				true, true, true, authorities);
	}

	private List<GrantedAuthority> buildUserAuthority(Set<Role> userRoles) {

		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

		// Build user's authorities
		for (Role userRole : userRoles) {
			setAuths.add(new SimpleGrantedAuthority(userRole.getDescription()));
		}

		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(
				setAuths);

		return Result;
	}

}
