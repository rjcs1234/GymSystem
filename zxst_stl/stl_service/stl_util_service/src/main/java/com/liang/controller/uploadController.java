package com.liang.controller;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.liang.util.Result;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * @program: zxst_stl
 * @ClassName uploadController
 * @description:
 * @author: 梁海富
 * @create: 2025−06-23 10:56
 * @Version 1.0
 **/

@RestController
@RequestMapping("/file")
public class uploadController {

    //oss上传
    @Value("${alinfo.oss-endpoint}")
    private String endpoint;
    @Value("${alinfo.oss-accesskeyid}")
    private String accessKeyId;
    @Value("${alinfo.oss-accesskeysecret}")
    private String accessKeySecret;
    @Value("${alinfo.oss-bucketname}")
    private String bucketName;

    @RequestMapping("/upload")
    public Result savePic(@RequestParam("file") MultipartFile file) {
        System.out.println(endpoint+"\t"+accessKeyId+"\t"+accessKeySecret+"\t"+bucketName+"\t"+file.getOriginalFilename());
        // 填写Object完整路径，完整路径中不能包含Bucket名称，例如exampledir/exampleobject.txt。
        String filename = file.getOriginalFilename(); // 4.5.6.7.82.jpg
        String uuidStr = UUID.randomUUID().toString().replace("-",""); // uuid字符串
        System.out.println(uuidStr);
        filename = filename.substring(filename.lastIndexOf("."));
        String newFileName = uuidStr + filename;
        String objectName = "stl/"+newFileName;
        // 创建OSS对象
        OSS ossClient = null;
        try {
            ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            //上传
            ossClient.putObject(bucketName, objectName, file.getInputStream());
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            ossClient.shutdown();
        }
        //构建path  https://zxst-shoop-new.oss-cn-qingdao.aliyuncs.com/admin.jpg
        String path = "https://zxst-shop-liang.oss-cn-qingdao.aliyuncs.com/" + objectName;
        return Result.success(path);
    }
}
