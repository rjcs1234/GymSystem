package com.liang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liang.entity.Member;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zxst
 * @since 2025-06-17
 */
public interface IMemberService extends IService<Member> {

    boolean add(Member member);

    boolean deleteMemberById(Integer id);
}
