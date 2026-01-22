package com.dd.admin.system.dept.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dd.admin.system.dept.entity.Dept;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dd.admin.system.dept.domain.DeptVo;
import com.dd.admin.system.dept.domain.DeptDto;
import java.util.List;

/**
 * <p>
 * 机构 服务类
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2022-01-04
 */
public interface DeptService extends IService<Dept> {

    //机构-分页列表
    IPage<DeptVo> selectDeptPage(DeptDto deptDto);

    //机构-列表
    List<DeptVo> selectDeptList(DeptDto deptDto);

}
