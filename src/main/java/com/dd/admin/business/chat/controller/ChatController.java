package com.dd.admin.business.chat.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.extra.pinyin.PinyinUtil;
import com.dd.admin.business.api.domain.UnReadCountBean;
import com.dd.admin.business.chat.domain.*;
import com.dd.admin.common.aop.operationLog.aop.OperLog;
import com.dd.admin.common.aop.operationLog.aop.OperType;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.dd.admin.common.model.UpdateGroup;
import com.dd.admin.common.model.result.ResultBean;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.NotBlank;
import com.dd.admin.business.chat.entity.Chat;
import com.dd.admin.business.chat.service.ChatService;
import com.dd.admin.common.util.MinioUrlUtil;

import java.util.*;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-12-28
 */
@Api(tags = "")
@RestController
public class ChatController {

    @Autowired
    ChatService chatService;
    
    @Autowired
    private MinioUrlUtil minioUrlUtil;

    /**
     * 将给定的Date对象转换为对应东八区（Asia/Shanghai）的时间戳
     *
     * @param createTime 要转换的Date对象
     * @return 对应东八区的时间戳（以毫秒为单位）
     */
    public static long convertToShanghaiTimeZoneTimestamp(Date createTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(createTime);
        calendar.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        return calendar.getTimeInMillis();
    }


    @ApiOperation(value = "查询客服未读消息数量")
    @ApiOperationSupport(order = 1)
    @GetMapping("/admin/chat/getUnReadCount")
    @OperLog(operModule = "查询客服未读消息数量",operType = OperType.OTHER,operDesc = "查询客服未读消息数量")
    public ResultBean getUnReadCount() {
        Integer chatUnReadCount = chatService.selectUnReadCount("8");
        return ResultBean.success(chatUnReadCount);
    };

    @ApiOperation(value = "作者列表")
    @ApiOperationSupport(order = 2)
    @GetMapping("/admin/chat/authorList")
    public ResultBean<List<AuthorChat>> authorList(String authorId) {
        List<AuthorChat> authorChats = chatService.selectAuthorChatList(authorId);
        authorChats.stream().forEach(authorChat -> {
            try {
                authorChat.setIndex(String.valueOf(PinyinUtil.getFirstLetter(authorChat.getIndex().charAt(0))));
                if(authorChat.getCreateTime()!=null){
                    authorChat.setLastSendTime(convertToShanghaiTimeZoneTimestamp( authorChat.getCreateTime()));
                }
                // 刷新头像URL
                authorChat.setAvatar(minioUrlUtil.refreshMinioUrl(authorChat.getAvatar()));
            }catch (Exception e){

            }
        });
        return ResultBean.success(authorChats);
    }

    @ApiOperation(value = "作者相关聊天信息")
    @ApiOperationSupport(order = 2)
    @GetMapping("/admin/chat/getAuthorChat")
    public ResultBean<IPage<MessageBean>> getAuthorChat(String authorId, String fromId) {
        ChatDto chatDto = new ChatDto();
        chatDto.setFromId(fromId);
        chatDto.setToId(authorId);

        IPage chatPage = chatService.selectChatPage(chatDto);
        List<ChatVo> records = chatPage.getRecords();
        List<MessageBean> messageBeanList = new ArrayList<>();
        records.stream().forEach(chatVo -> {
            MessageBean messageBean = new MessageBean();
            messageBean.setId(chatVo.getChatId());
            messageBean.setContent(chatVo.getContent());
            messageBean.setSendTime(convertToShanghaiTimeZoneTimestamp(chatVo.getCreateTime()));
            messageBean.setStatus("succeed");
            messageBean.setType("text");
            messageBean.setToContactId(chatVo.getToId());
            MessageBean.FromUser fromUser = new MessageBean.FromUser();
            fromUser.setAvatar(minioUrlUtil.refreshMinioUrl(chatVo.getFromAvatar()));
            fromUser.setDisplayName(chatVo.getFromName());
            fromUser.setId(chatVo.getFromId());
            messageBean.setFromUser(fromUser);
            messageBeanList.add(messageBean);
        });
        chatPage.setRecords(messageBeanList);

        return ResultBean.success(chatPage);
    }



    @ApiOperation(value = "读取聊天消息")
    @ApiOperationSupport(order = 4)
    @PostMapping("/admin/chat/readAuthorMessage")
    @OperLog(operModule = "读取回复消息", operType = OperType.OTHER, operDesc = "读取聊天消息")
    public ResultBean readReplyMessage(@RequestBody AuthorParam authorParam) {
        chatService.readMessage(authorParam.getAuthorId(),"8");
        return ResultBean.success("noAlert");
    }


    @ApiOperation(value = "-分页列表")
    @ApiOperationSupport(order = 1)
    @GetMapping("/admin/chat/page")
    public ResultBean<IPage<ChatVo>> page(ChatDto chatDto) {
        IPage<ChatVo> pageInfo = chatService.selectChatPage(chatDto);
        // 刷新资源URL
        if (pageInfo != null && pageInfo.getRecords() != null) {
            pageInfo.getRecords().forEach(chatVo -> {
                chatVo.setResourceUrl(minioUrlUtil.refreshMinioUrl(chatVo.getResourceUrl()));
            });
        }
        return ResultBean.success(pageInfo);
    }

    @ApiOperation(value = "-列表")
    @ApiOperationSupport(order = 2)
    @GetMapping("/admin/chat/list")
    public ResultBean<List<ChatVo>> list(ChatDto chatDto) {
        List<ChatVo> list = chatService.selectChatDetail(chatDto);
        // 刷新资源URL
        if (list != null) {
            list.forEach(chatVo -> {
                chatVo.setResourceUrl(minioUrlUtil.refreshMinioUrl(chatVo.getResourceUrl()));
            });
        }
        return ResultBean.success(list);
    }

    @ApiOperation(value = "-添加")
    @ApiOperationSupport(order = 3)
    @PostMapping("/admin/chat/add")
    public ResultBean<Chat> add(@RequestBody @Validated ChatDto chatDto) {
        Chat chat = BeanUtil.copyProperties(chatDto, Chat.class);
        chatService.save(chat);
        return ResultBean.success(chat);
    }

    @ApiOperation(value = "-查询")
    @ApiOperationSupport(order = 4)
    @GetMapping("/admin/chat/{chatId}")
    public ResultBean<ChatVo> get(@PathVariable @NotBlank String chatId) {
        Chat chat = chatService.getById(chatId);
        ChatVo chatVo = BeanUtil.copyProperties(chat,ChatVo.class);
        // 刷新资源URL
        chatVo.setResourceUrl(minioUrlUtil.refreshMinioUrl(chatVo.getResourceUrl()));
        return ResultBean.success(chatVo);
    }

    @ApiOperation(value = "-修改")
    @ApiOperationSupport(order = 5)
    @PostMapping("/admin/chat/update")
    public ResultBean<Chat> update(@RequestBody @Validated(UpdateGroup.class) ChatDto chatDto) {
        Chat chat = BeanUtil.copyProperties(chatDto, Chat.class);
        chatService.updateById(chat);
        return ResultBean.success(chat);
    }

    @ApiOperation(value = "-删除")
    @ApiOperationSupport(order = 6)
    @GetMapping("/admin/chat/delete/{chatId}")
    public ResultBean<Chat> delete(@PathVariable @NotBlank String chatId) {
        Boolean b = chatService.removeById(chatId);
        return ResultBean.success(b);
    }
}
