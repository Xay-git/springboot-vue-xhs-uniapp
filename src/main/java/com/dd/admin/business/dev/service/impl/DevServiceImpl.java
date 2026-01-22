package com.dd.admin.business.dev.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dd.admin.common.model.PageFactory;
import com.dd.admin.business.dev.entity.Dev;
import com.dd.admin.business.dev.mapper.DevMapper;
import com.dd.admin.business.dev.service.DevService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.dd.admin.business.dev.domain.DevVo;
import com.dd.admin.business.dev.domain.DevDto;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-02-11
 */
@Service
public class DevServiceImpl extends ServiceImpl<DevMapper, Dev> implements DevService {

    @Override
    public IPage<DevVo> selectDevPage(DevDto devDto) {
        Page page = PageFactory.defaultPage();
        return baseMapper.selectDevPage(page,devDto);
    }

    @Override
    public List<DevVo> selectDevList(DevDto devDto) {
        return baseMapper.selectDevList(devDto);
    }
}
