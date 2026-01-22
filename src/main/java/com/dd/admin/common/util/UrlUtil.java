package com.dd.admin.common.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * URL工具类，用于生成适应不同环境的URL
 */
@Component
public class UrlUtil {

    @Value("${server.port}")
    private String port;

    @Value("${dd.domain:}")
    private String domain;

    @Value("${dd.use-https:false}")
    private boolean useHttps;

    /**
     * 根据配置生成文件访问URL
     * 
     * @param request HTTP请求
     * @param filePath 文件路径
     * @return 完整的URL
     */
    public String getFileUrl(HttpServletRequest request, String filePath) {
        // 如果配置了域名，则使用域名
        if (domain != null && !domain.isEmpty()) {
            String protocol = useHttps ? "https://" : "http://";
            return protocol + domain + filePath;
        }
        
        // 否则使用服务器名称和端口
        String serverName = request.getServerName();
        String protocol = request.isSecure() ? "https://" : "http://";
        return protocol + serverName + ":" + port + filePath;
    }
} 