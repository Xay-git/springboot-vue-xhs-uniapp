package com.dd.admin.system.dept.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dd.admin.common.model.PageFactory;
import com.dd.admin.system.dept.entity.Dept;
import com.dd.admin.system.dept.mapper.DeptMapper;
import com.dd.admin.system.dept.service.DeptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.dd.admin.system.dept.domain.DeptVo;
import com.dd.admin.system.dept.domain.DeptDto;
import java.util.List;

/**
 * <p>
 * 机构 服务实现类
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2022-01-04
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements DeptService {

    @Override
    public IPage<DeptVo> selectDeptPage(DeptDto deptDto) {
        Page page = PageFactory.defaultPage();
        return baseMapper.selectDeptPage(page,deptDto);
    }

    @Override
    public List<DeptVo> selectDeptList(DeptDto deptDto) {
        return baseMapper.selectDeptList(deptDto);
    }
}
