package com.dd.admin.business.follow.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dd.admin.business.follow.entity.Follow;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dd.admin.business.follow.domain.FollowVo;
import com.dd.admin.business.follow.domain.FollowDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 关注表 服务类
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-12-24
 */
public interface FollowService extends IService<Follow> {

    //关注表-分页列表
    IPage<FollowVo> selectFollowPage(FollowDto followDto);

    //关注表-列表
    List<FollowVo> selectFollowList(FollowDto followDto);

    List<Follow> selectFollowListByFollowId(String followId);

     Follow selectOneByFollowId(String authorId,String followId);


    List<FollowVo> selectMyFollowList( String authorId,String targetId);
    List<FollowVo> selectFollowMeList(String authorId,String targetId);
    List<FollowVo> getMyFriends(String authorId);
    //未读聊天消息的数量
    Integer selectUnReadCount(String authorId);

    void readMessage(String authorId);

}
