package com.course.user.service.impl;

import com.course.model.user.UserInfo;
import com.course.user.mapper.UserInfoMapper;
import com.course.user.service.UserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author YIFU
 * @since 2022-07-03
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

}
