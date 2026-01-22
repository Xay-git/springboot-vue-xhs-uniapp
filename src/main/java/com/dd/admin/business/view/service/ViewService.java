package com.dd.admin.business.view.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dd.admin.business.view.entity.View;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dd.admin.business.view.domain.ViewVo;
import com.dd.admin.business.view.domain.ViewDto;
import java.util.List;

/**
 * <p>
 * 查看 服务类
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-01-15
 */
public interface ViewService extends IService<View> {

    //查看-分页列表
    IPage<ViewVo> selectViewPage(ViewDto viewDto);

    //查看-列表
    List<ViewVo> selectViewList(ViewDto viewDto);


    View viewNote(String authorId,String noteId);

}
