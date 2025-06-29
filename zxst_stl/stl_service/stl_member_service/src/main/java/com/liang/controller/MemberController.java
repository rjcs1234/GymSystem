package com.liang.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liang.entity.Member;
import com.liang.service.IMemberService;
import com.liang.util.MessageContant;
import com.liang.util.Result;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: zxst_stl
 * @ClassName MemberController
 * @description:
 * @author: 梁海富
 * @create: 2025−06-17 16:11
 * @Version 1.0
 **/

@RestController
@RequestMapping("/member")
public class MemberController {

    @Resource
    private IMemberService iMemberService;

    //添加成员
    @PostMapping
    public Result saveMember(@RequestBody Member member){
        boolean flag=false;
        String phone=member.getPhone();
        //根据用户手机号查询是否存在
        LambdaQueryWrapper<Member> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Member::getPhone,phone);
        Member one = iMemberService.getOne(lambdaQueryWrapper);
        if(one!=null){
            return Result.error("手机号已经被注册");
        }else{
            //添加会员
            flag=iMemberService.add(member);
        }
        if (flag) {
            return Result.success();
        }else{
            return Result.error();
        }
    }

    //分页展示会员信息
    @GetMapping
    public Result showMemberInfo(
            @RequestParam(name = "currentPage",required = false,defaultValue = "1") Integer currentPage,
            @RequestParam(name = "pageSize",required = false,defaultValue = "10")  Integer pageSize,
            String search
    ){
        Page<Member> page=new Page<>(currentPage,pageSize);
        LambdaQueryWrapper<Member> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        if(search!=null && !"".equals(search)){
            lambdaQueryWrapper.like(Member::getName,search);
            lambdaQueryWrapper.or().like(Member::getPhone,search);
        }
        iMemberService.page(page,lambdaQueryWrapper);
        return Result.success(page);
    }

    //会员更新
    @PutMapping
    public  Result updateMember(@RequestBody Member member){
        boolean flag=false;
         flag= iMemberService.updateById(member);
        if (flag) {
            return Result.success(MessageContant.UPDATE_SUCCESS);
        }else{
            return Result.error(MessageContant.UPDATE_FAIL);
        }
    }
    //会员删除 里面包含feign调用
    @DeleteMapping("/{id}")
    public Result deleteMember(@PathVariable(name = "id") Integer id){
        boolean flag=false;
        flag=iMemberService.deleteMemberById(id);
        if (flag) {
            return Result.success(MessageContant.DELETE_SUCCESS);
        }else{
            return Result.error(MessageContant.DELETE_FAIL);
        }
    }

    @GetMapping("/getOneMemeber")
    public Member getOneMemeber(@RequestParam("id") Integer id){
        Member byId = iMemberService.getById(id);
        return byId;
    }

    @GetMapping("/all")
    public Result all(){
        List<Member> list = iMemberService.list();
        return Result.success(0,null,list);
    }

}
