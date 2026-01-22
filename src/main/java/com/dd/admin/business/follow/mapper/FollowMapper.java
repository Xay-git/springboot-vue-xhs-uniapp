package com.dd.admin.business.follow.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dd.admin.business.follow.entity.Follow;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dd.admin.business.follow.domain.FollowVo;
import com.dd.admin.business.follow.domain.FollowDto;

import java.util.List;

/**
 * <p>
 * 关注表 Mapper 接口
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-12-24
 */
@Mapper
public interface FollowMapper extends BaseMapper<Follow> {

    IPage<FollowVo> selectFollowPage(Page<FollowVo> page, @Param("followDto") FollowDto followDto);

    List<FollowVo> selectFollowList(@Param("followDto") FollowDto followDto);

    //查询用户为authorId的粉丝列表 再传入一个数值 看和当前数值的关联关系 例如我看对方明天 想知道对方和我的关系
    List<FollowVo> selectMyFollowList(@Param("authorId") String authorId,@Param("targetId") String targetId);
    List<FollowVo> selectFollowMeList(@Param("authorId") String authorId,@Param("targetId") String targetId);
    List<FollowVo> getMyFriends(@Param("authorId")String authorId);


}
