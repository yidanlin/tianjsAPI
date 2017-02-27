package com.tianan.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianan.mapper.UserMapper;
import com.tianan.model.User;
import com.tianan.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper userMapper;
	
	//使用mybatis
	public List<User> likeName(String name) {
		return userMapper.likeName(name);
	}

	public User getById(Long id) {
		return userMapper.getById(id);
	}

	public List<User> getUsers() {
		return userMapper.getUsers();
	}

	@Override
	public String insert(User user) {
		String result = null;
		List<User> users = likeName(user.getName());
		if(users.size()>0){
			result = "注册失败，用户名重复";
		}else{
			userMapper.insert(user);
		}
		return result;
	}
}
