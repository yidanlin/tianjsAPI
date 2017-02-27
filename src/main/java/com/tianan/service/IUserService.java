package com.tianan.service;

import com.baomidou.mybatisplus.service.IService;
import com.tianan.model.User;

/**
 *
 * User 表数据服务层接口
 *
 */
public interface IUserService extends IService<User> {

	boolean deleteAll();

}