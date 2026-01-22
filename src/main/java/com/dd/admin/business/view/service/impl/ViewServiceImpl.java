package com.dd.admin.business.view.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dd.admin.common.model.PageFactory;
import com.dd.admin.business.view.entity.View;
import com.dd.admin.business.view.mapper.ViewMapper;
import com.dd.admin.business.view.service.ViewService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.dd.admin.business.view.domain.ViewVo;
import com.dd.admin.business.view.domain.ViewDto;
import java.util.List;

/**
 * <p>
 * 查看 服务实现类
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-01-15
 */
@Service
public class ViewServiceImpl extends ServiceImpl<ViewMapper, View> implements ViewService {

    @Override
    public IPage<ViewVo> selectViewPage(ViewDto viewDto) {
        Page page = PageFactory.defaultPage();
        return baseMapper.selectViewPage(page,viewDto);
    }

    @Override
    public List<ViewVo> selectViewList(ViewDto viewDto) {
        return baseMapper.selectViewList(viewDto);
    }

    @Override
    public View viewNote(String authorId, String noteId) {
        View view = new View();
        view.setAuthorId(authorId);
        view.setNoteId(noteId);
        save(view);
        return view;
    }
}
