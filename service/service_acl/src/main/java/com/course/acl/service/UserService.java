package com.course.acl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.course.acl.entity.User;


public interface UserService extends IService<User> {

    // 从数据库中取出用户信息
    User selectByUsername(String username);
}
