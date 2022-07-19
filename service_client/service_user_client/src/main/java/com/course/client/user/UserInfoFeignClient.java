package com.course.client.user;

import com.course.model.user.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-user")
public interface UserInfoFeignClient {

    @GetMapping("/admin/user/userInfo/inner/getById/{id}")
    UserInfo getById(@PathVariable Long id);

}
