package com.dd.admin.business.note.domain;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value="笔记表返回对象")
public class ReplayMeVo {
    private String replyId;
    // 对应r.note_id，假设在数据库中为字符串类型，如果是其他类型可自行调整
    private String noteId;
    // 对应b.note_title，假设为字符串类型存储笔记标题
    private String noteTitle;
    // 对应a.author_id，假设为字符串类型的作者ID
    private String authorId;
    // 对应a.author_name，字符串类型的作者姓名
    private String authorName;
    // 对应a.AVATAR_URL，字符串类型的作者头像链接
    private String avatarUrl;
    // 对应r.create_time，这里使用Java的Date类型表示创建时间，根据实际数据库字段类型可能需要调整，比如使用LocalDateTime等更合适的时间类型
    private String createTime;
    // 对应r.REPLAY_CONTENT，回复内容，假设为字符串类型
    private String replayContent;
    private String parentReplayContent;
    // 对应b.FIRST_PICTURE，笔记首张图片相关信息，假设为字符串类型，比如图片路径或者URL等，根据实际业务存储情况调整
    private String firstPicture;
    private String type;  // 1回复笔记: 2回复评论:
    private String noteType;
}
