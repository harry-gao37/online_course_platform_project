package com.course.client;

import com.course.model.vod.Course;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "service-vod")
public interface CourseFeignClient {

    @GetMapping("/api/vod/course/inner/findByKeyword/{keyword}")
    List<Course> findByKeyword(@PathVariable String keyword);
}
