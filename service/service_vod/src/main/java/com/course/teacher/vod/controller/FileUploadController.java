package com.course.teacher.vod.controller;


import com.course.result.Result;
import com.course.teacher.vod.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "File Uploading Interface")
@RestController
@RequestMapping("/admin/vod/file")
//@CrossOrigin
public class FileUploadController {


    @Autowired
    private FileService fileService;

    @ApiOperation("Uploading Files")
    @PostMapping("upload")
    public Result uploadFile(MultipartFile file){
        String url = fileService.upload(file);
        return Result.ok(url).message("Successfully uploading files");

    }

}
