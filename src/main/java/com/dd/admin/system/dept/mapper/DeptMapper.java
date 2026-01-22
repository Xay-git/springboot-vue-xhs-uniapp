package com.dd.admin.system.dept.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dd.admin.system.dept.entity.Dept;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dd.admin.system.dept.domain.DeptVo;
import com.dd.admin.system.dept.domain.DeptDto;

import java.util.List;

/**
 * <p>
 * 机构 Mapper 接口
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2022-01-04
 */
@Mapper
public interface DeptMapper extends BaseMapper<Dept> {

    IPage<DeptVo> selectDeptPage(Page<DeptVo> page, @Param("deptDto") DeptDto deptDto);

    List<DeptVo> selectDeptList(@Param("deptDto") DeptDto deptDto);
}
