package com.course.teacher.vod.controller;

import com.course.result.Result;
import com.course.teacher.vod.service.VodService;
import com.course.teacher.vod.utils.ConstantPropertiesUtil;
import com.course.teacher.vod.utils.Signature;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@Api(tags = "Video On-demand Interface")
@RestController
@RequestMapping("/admin/vod")
//@CrossOrigin
public class VodController {

    @Autowired
    private VodService vodService;

    //客户端上传视频签名返回
    @GetMapping("sign")
    public Result sign() {
        Signature sign = new Signature();
        // 设置 App 的云 API 密钥
        sign.setSecretId(ConstantPropertiesUtil.ACCESS_KEY_ID);
        sign.setSecretKey(ConstantPropertiesUtil.ACCESS_KEY_SECRET);
        sign.setCurrentTime(System.currentTimeMillis() / 1000);
        sign.setRandom(new Random().nextInt(java.lang.Integer.MAX_VALUE));
        sign.setSignValidDuration(3600 * 24 * 2); // 签名有效期：2天
        try {
            String signature = sign.getUploadSignature();
            System.out.println("signature : " + signature);
            return Result.ok(signature);
        } catch (Exception e) {
            System.out.print("获取签名失败");
            e.printStackTrace();
            return Result.fail(null);
        }
    }


    //服务端上传视频接口
    @PostMapping("upload")
    public Result uploadVideo(){
        String fileId = vodService.updateVideo();
        return Result.ok(fileId);

    }

    //删除视频
    @DeleteMapping("remove/{videoSourceId}")
    public Result removeVideo(@PathVariable String videoSourceId){
        vodService.removeVideo(videoSourceId);
        return Result.ok(null);


    }

}
