package com.course.user.controller;


import com.course.model.user.UserInfo;
import com.course.user.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author YIFU
 * @since 2022-07-03
 */
@Api(tags = "User Info Interface")
@RestController
@RequestMapping("/admin/user/userInfo")
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;


    @ApiOperation(value = "获取")
    @GetMapping("inner/getById/{id}")
    public UserInfo getById(@PathVariable Long id) {
        return userInfoService.getById(id);
    }
}

