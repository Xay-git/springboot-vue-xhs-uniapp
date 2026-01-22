package com.dd.admin.business.dev.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dd.admin.business.dev.entity.Dev;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dd.admin.business.dev.domain.DevVo;
import com.dd.admin.business.dev.domain.DevDto;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-02-11
 */
public interface DevService extends IService<Dev> {

    //-分页列表
    IPage<DevVo> selectDevPage(DevDto devDto);

    //-列表
    List<DevVo> selectDevList(DevDto devDto);

}
