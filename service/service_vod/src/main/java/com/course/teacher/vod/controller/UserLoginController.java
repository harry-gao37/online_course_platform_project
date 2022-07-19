package com.course.teacher.vod.controller;

import com.course.result.Result;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
@Api(tags = "User Login Interface")
@RestController
@RequestMapping("/admin/vod/user")
//@CrossOrigin //跨域
public class UserLoginController {

    /* Login */
    @PostMapping("login")
    public Result login(){
        Map<String,Object> map = new HashMap<>();
        map.put("token","admin-token");
        return Result.ok(map).code(20000);
    }


    /* info */
    @GetMapping("info")
    public Result info(){
        Map<String,Object> map = new HashMap<>();
        map.put("roles","admin");
        map.put("introduction","I am a super administrator");
        map.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        map.put("name","Super Admin");
        return Result.ok(map).code(20000);
    }
}
