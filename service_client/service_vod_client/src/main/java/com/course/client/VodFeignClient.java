package com.course.client;


import com.course.vo.vod.CourseOrderVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-vod")
public interface VodFeignClient {

    @GetMapping("/edu/courseFront/getCourseInfoOrder/{id}")
    CourseOrderVo getCourseInfoOrder(@PathVariable("id") String id);
}
