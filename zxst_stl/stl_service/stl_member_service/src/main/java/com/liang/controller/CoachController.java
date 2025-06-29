package com.liang.controller;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liang.entity.Coach;
import com.liang.entity.Course;
import com.liang.feign.CourseFeign;
import com.liang.service.ICoachService;
import com.liang.util.MessageContant;
import com.liang.util.Result;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: zxst_stl
 * @ClassName coachController
 * @description:
 * @author: 梁海富
 * @create: 2025−06-23 10:11
 * @Version 1.0
 **/

@RestController
@RequestMapping("/coach")
public class CoachController {
    @Resource
    private ICoachService iCoachService;

    @Resource
    private CourseFeign courseFeign;

    //oss上传
    @Value("${alinfo.oss-endpoint}")
    private String endpoint;
    @Value("${alinfo.oss-accesskeyid}")
    private String accessKeyId;
    @Value("${alinfo.oss-accesskeysecret}")
    private String accessKeySecret;
    @Value("${alinfo.oss-bucketname}")
    private String bucketName;

    @RequestMapping("/getCoachApi")
    public Result getCoachApi(
            @RequestParam(name = "currentPage",required = false,defaultValue = "1") Integer currentPage,
            @RequestParam(name = "pageSize",required = false,defaultValue = "10") Integer pageSize,
            String search){
        Page<Coach> page=new Page<>(currentPage,pageSize);
        LambdaQueryWrapper <Coach> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        if(search!=null && !"".equals(search)){
            lambdaQueryWrapper.like(Coach::getName,search);
            lambdaQueryWrapper.or().like(Coach::getPhone,search);
        }
        iCoachService.page(page,lambdaQueryWrapper);
        return  Result.success(page);
    }

    @PostMapping("/addCoach")
    public  Result addCoach(@RequestBody Coach coach) throws  Exception{
        try {
            LambdaQueryWrapper<Coach> lambdaQueryWrapper=new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(Coach::getPhone,coach.getPhone());
            Coach duCoach = iCoachService.getOne(lambdaQueryWrapper);
            if(duCoach!=null) {
                //已存在,添加失败
                throw new Exception("该手机号已存在");
            }
            boolean save = iCoachService.save(coach);
            if(save){
                return Result.success();
            }else{
                return Result.error();
            }
        }catch (Exception e){
            return  Result.error(e.getMessage());
        }
    }

    //教练删除 包含远程feign调用
    @DeleteMapping("/{id}")
    public Result deleteCoach(@PathVariable(name = "id") Integer id){
        //远程调用courseFeign，查询是否该教练是否有在教课程
        List<Course> courseByID = courseFeign.getCourseByID(id);
        //该教练有课程
        if(courseByID!=null &&courseByID.size()>0){
            return Result.error("删除失败");
        }
        OSS ossClient = null;
        boolean flag=false;
        //删除关联的图片
        String path = "https://zxst-shoop-new.oss-cnqingdao.aliyuncs.com/";
        String floderprifex = "stl/";
        //查询教练信息
        LambdaQueryWrapper <Coach> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Coach::getId,id);
        Coach one = iCoachService.getOne(lambdaQueryWrapper);
        //https://zxst-shoop-new.oss-cnqingdao.aliyuncs.com/admin/ad79caf4d7de4443a842d792180863f7.jpg
        String photo = one.getPhoto();//
        String fileName = photo.substring(photo.lastIndexOf("/")+1, photo.length());//ad79caf4d7de4443a842d792180863f7.jpg
        ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ossClient.deleteObject(bucketName,floderprifex+fileName);
        //如果没有课程关联 删除教练信息
        flag=iCoachService.removeById(id);
        if (flag) {
            return Result.success(MessageContant.DELETE_SUCCESS);
        }else{
            return Result.error(MessageContant.DELETE_FAIL);
        }
    }

    //更新教练信息
    @RequestMapping("/update")
    public  Result update(@RequestBody Coach coach){
        boolean b = iCoachService.updateById(coach);
        if(b){
            return Result.success();
        }else{
            return  Result.error();
        }
    }

    //根据教练id查询教练信息
    @GetMapping("/getOneCocaById")
    public Coach getOneCoachById(@RequestParam("id") Integer id){
        return iCoachService.getById(id);
    }


}
