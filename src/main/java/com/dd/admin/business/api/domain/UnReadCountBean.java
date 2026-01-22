package com.dd.admin.business.api.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnReadCountBean {
    private Integer chatUnReadCount;
    private Integer upNotesUnReadCount;
    private Integer starNotesUnReadCount;
    private Integer replyUnReadCount;
    private Integer followUnReadCount;
    private Integer receiveUnReadCount;
    private Integer totalUnReadCount;

    // 自定义方法，用于计算总数量（也可以在构造函数等其他地方调用此方法来计算总和）
    public void calculateTotalUnReadCount() {
        int chatCount = chatUnReadCount!= null? chatUnReadCount : 0;
        int upNotesCount = upNotesUnReadCount!= null? upNotesUnReadCount : 0;
        int starNotesCount = starNotesUnReadCount!= null? starNotesUnReadCount : 0;
        int replyCount = replyUnReadCount!= null? replyUnReadCount : 0;
        int followCount = followUnReadCount!= null? followUnReadCount : 0;
        int receiveCount = receiveUnReadCount!= null? receiveUnReadCount : 0;

        totalUnReadCount = chatCount + upNotesCount + starNotesCount + replyCount + followCount + receiveCount;
    }
}
