package com.course.acl.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.course.acl.entity.RolePermission;
import com.course.acl.mapper.RolePermissionMapper;
import com.course.acl.service.RolePermissionService;
import org.springframework.stereotype.Service;

@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService {

}
