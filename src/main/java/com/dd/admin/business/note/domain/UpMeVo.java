package com.dd.admin.business.note.domain;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value="笔记表返回对象")
public class UpMeVo {
    private String noteId;
    private String noteTitle;
    private String followId;
    private String followName;
    private String avatarUrl;
    private String createTime;
    private String firstPicture;
    private String type;  // 1点赞: 2收藏:
    private String  NoteType;
}
