package com.dd.admin.business.api;

import com.dd.admin.common.aop.operationLog.aop.OperLog;
import com.dd.admin.common.aop.operationLog.aop.OperType;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
@RestController
public class AiController {
    @GetMapping(value = "/ai/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @OperLog(operModule = "AI流式数据", operType = OperType.QUERY, operDesc = "获取AI流式数据")
    public SseEmitter streamData() {
        // 创建 SSE 发射器，设置超时时间（例如 1 分钟）
        SseEmitter emitter = new SseEmitter(60_000L);
        // 创建新线程，防止主程序阻塞
        new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    Thread.sleep(1000); // 模拟延迟
                    // 发送数据
                    emitter.send("time=" + System.currentTimeMillis());
                }
                // 发送完毕
                emitter.complete();
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        }).start();
        return emitter;
    }

}
