package com.dd.admin.system.dept.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dd.admin.common.model.UpdateGroup;
import com.dd.admin.common.model.result.ResultBean;
import com.dd.admin.common.service.CommonService;
import com.dd.admin.common.utils.ColumnUtil;
import com.dd.admin.system.dept.domain.DeptDto;
import com.dd.admin.system.dept.domain.DeptTree;
import com.dd.admin.system.dept.domain.DeptVo;
import com.dd.admin.system.dept.entity.Dept;
import com.dd.admin.system.dept.service.DeptService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * <p>
 * 机构 前端控制器
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2022-01-04
 */
@Api(tags = "机构")
@RestController
public class DeptController {

    @Autowired
    DeptService deptService;

    @Autowired
    CommonService commonService;

    @ApiOperation(value = "机构-分页列表")
    @ApiOperationSupport(order = 1)
    @GetMapping("/admin/dept/page")
    public ResultBean<IPage<DeptVo>> page(DeptDto deptDto) {
        IPage<DeptVo> pageInfo = deptService.selectDeptPage(deptDto);
        return ResultBean.success(pageInfo);
    }

    @ApiOperation(value = "机构-列表")
    @ApiOperationSupport(order = 2)
    @GetMapping("/admin/dept/list")
    public ResultBean<List<DeptVo>> list(DeptDto deptDto) {
        List<DeptVo> list = deptService.selectDeptList(deptDto);
        return ResultBean.success(list);
    }

    @ApiOperation(value = "机构-添加")
    @ApiOperationSupport(order = 3)
    @PostMapping("/admin/dept/add")
    public ResultBean<Dept> add(@RequestBody @Validated DeptDto deptDto) {
        Dept dept = BeanUtil.copyProperties(deptDto, Dept.class);
        Dept parentDept = deptService.getById(deptDto.getParentId());
        dept.setParentIds(parentDept.getParentIds()+ parentDept.getDeptId()+ ",");
        dept.setParentName(parentDept.getDeptName());
        deptService.save(dept);
        return ResultBean.success(dept);
    }

    @ApiOperation(value = "机构-查询")
    @ApiOperationSupport(order = 4)
    @GetMapping("/admin/dept/{deptId}")
    public ResultBean<DeptVo> get(@PathVariable @NotBlank String deptId) {
        Dept dept = deptService.getById(deptId);
        DeptVo deptVo = BeanUtil.copyProperties(dept,DeptVo.class);
        return ResultBean.success(deptVo);
    }

    @ApiOperation(value = "机构-修改")
    @ApiOperationSupport(order = 5)
    @PostMapping("/admin/dept/update")
    public ResultBean<Dept> update(@RequestBody @Validated(UpdateGroup.class) DeptDto deptDto) {
        Dept dept = BeanUtil.copyProperties(deptDto, Dept.class);
        deptService.updateById(dept);
        return ResultBean.success(dept);
    }

    @ApiOperation(value = "机构-删除")
    @ApiOperationSupport(order = 6)
    @GetMapping("/admin/dept/delete/{deptId}")
    public ResultBean<Dept> delete(@PathVariable @NotBlank String deptId) {
        Boolean b = deptService.removeById(deptId);
        return ResultBean.success(b);
    }

    @ApiOperation(value = "机构-tree")
    @ApiOperationSupport(order = 6)
    @GetMapping("/admin/dept/tree")
    public ResultBean<List<DeptTree>> tree() {
        List<Dept> deptList = deptService.list();
        List<DeptTree> treeList =  BeanUtil.copyToList(deptList,DeptTree.class);
        List<DeptTree> tree = commonService.generateSubs(treeList, ColumnUtil.getName(DeptTree::getDeptId));
        return ResultBean.success(tree);
    }
}
