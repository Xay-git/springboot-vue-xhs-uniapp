package com.dd.admin.business.author.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dd.admin.business.author.entity.Author;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dd.admin.business.author.domain.AuthorVo;
import com.dd.admin.business.author.domain.AuthorDto;

import java.util.List;

/**
 * <p>
 * 作者（博主） Mapper 接口
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-12-12
 */
@Mapper
public interface AuthorMapper extends BaseMapper<Author> {

    IPage<AuthorVo> selectAuthorPage(Page<AuthorVo> page, @Param("authorDto") AuthorDto authorDto);

    IPage<AuthorVo> selectAuthorList(Page<AuthorVo> page, @Param("authorDto") AuthorDto authorDto);

    Author selectAuthorByPhoneNumber(@Param("phoneNumber")  String phoneNumber);

    Long selectAuthorUpAndStarTotalCount(@Param("authorId")  String authorId);
}
