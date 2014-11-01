package com.app.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.app.model.User;

public class UserDAOImpl implements UserDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public User findUserByName(String username) {
		List<User> users = new ArrayList<User>();

		users = sessionFactory.getCurrentSession()
				.createQuery("from User where username=?")
				.setParameter(0, username).list();

		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}
	}

}
