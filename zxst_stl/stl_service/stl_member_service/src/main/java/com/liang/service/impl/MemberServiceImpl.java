package com.liang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liang.entity.Member;
import com.liang.entity.Reserve;
import com.liang.feign.ReserveFeign;
import com.liang.mapper.MemberMapper;
import com.liang.util.MD5Util;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.liang.service.IMemberService;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zxst
 * @since 2025-06-17
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements IMemberService {

    @Resource
    private  MemberMapper memberMapper;

    @Resource
    private ReserveFeign reserveFeign;

    @Override
    public boolean add(Member member) {
        String md5 = MD5Util.getMD5(member.getPassword(), member.getName());
        member.setPassword(md5);
        member.setCreateTime(new java.util.Date());
        return  memberMapper.insert(member)>0;
    }

    @Override
    public boolean deleteMemberById(Integer id) {
        boolean flag=false;
        // 利用feign调用 判断用户是否有订单(存在订单的用户无法删除)
        List<Reserve> list = reserveFeign.getReserveByMid(id);
        if (list != null && list.size() > 0) {
            return  flag;
        }else{
            flag = memberMapper.deleteById(id)>0;
        }
        return flag;
    }
}
