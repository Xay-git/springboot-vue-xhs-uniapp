package com.dd.admin.business.webSocket.handler;

import com.dd.admin.business.api.domain.UnReadCountBean;
import com.dd.admin.business.author.service.AuthorService;
import com.dd.admin.business.chat.service.ChatService;
import com.dd.admin.business.follow.service.FollowService;
import com.dd.admin.business.receive.service.ReceiveService;
import com.dd.admin.business.reply.service.ReplyService;
import com.dd.admin.business.starNotes.service.StarNotesService;
import com.dd.admin.business.upNotes.service.UpNotesService;
import com.dd.admin.business.webSocket.MsgHandlerInterface;
import com.dd.admin.business.webSocket.util.TioUtil;
import io.swagger.annotations.ApiModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tio.core.ChannelContext;

import java.util.Map;

import static com.dd.admin.business.webSocket.WsConst.HANDLER_TOTAL_COUNT;


@Slf4j
@Service("9")
@ApiModel("获取未读总数Handler")
public class GetApiReadCountMessageHandler implements MsgHandlerInterface {
    @Autowired
    ChatService chatService;
    @Autowired
    UpNotesService upNotesService;
    @Autowired
    StarNotesService starNotesService;
    @Autowired
    ReplyService replyService;
    @Autowired
    FollowService followService;
    @Autowired
    ReceiveService receiveService;
    @Override
    public Object handler(Map map, ChannelContext context) {
        String authorId = String.valueOf(map.get("authorId"));
        Integer chatUnReadCount = chatService.selectUnReadCount(authorId);
        Integer upNotesUnReadCount = upNotesService.selectUnReadCount(authorId);
        Integer starNotesUnReadCount = starNotesService.selectUnReadCount(authorId);
        Integer replyUnReadCount = replyService.selectUnReadCount(authorId);
        Integer followUnReadCount = followService.selectUnReadCount(authorId);
        Integer receiveUnReadCount = receiveService.selectUnReadCount(authorId);

        Integer totalUnReadCount = 0;
        UnReadCountBean unReadCountBean = new UnReadCountBean(chatUnReadCount,upNotesUnReadCount,starNotesUnReadCount,replyUnReadCount,followUnReadCount,receiveUnReadCount,totalUnReadCount);
        unReadCountBean.calculateTotalUnReadCount();

        TioUtil.sendChatMessageToUser(context.getGroupContext(),authorId,HANDLER_TOTAL_COUNT,unReadCountBean);
        return null;
    }
}
