package com.dd.admin.common.config;

import com.dd.admin.common.security.interceptor.ApiInterceptor;
import com.dd.admin.common.utils.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * web 配置
 *
 * @author 727869402@qq.com
 * @date 2021/7/6
 */
@Configuration
@EnableScheduling
public class GolbalConfig implements WebMvcConfigurer {

    @Autowired
    private ApiInterceptor apiInterceptor;

    // 注册拦截器的方法
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiInterceptor)
                .addPathPatterns("/api/auth/**"); // 拦截/api下所有请求路径
    }


    @Value("${dd.uploadPath}")
    private String uploadPath;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //设置允许跨域的路径
        registry.addMapping("/**")
                //设置允许跨域请求的域名
                .allowedOriginPatterns("*")
                //是否允许证书 不再默认开启
                .allowCredentials(true)
                //设置允许的方法
                .allowedMethods("GET", "POST")
                //跨域允许时间
                .maxAge(3600);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (ToolUtil.isWinOs()) {  //如果是Windows系统
            registry.addResourceHandler("/upload/**")
                    // /upload/**表示在磁盘upload目录下的所有资源会被解析为以下的路径
                    .addResourceLocations("file:" + uploadPath) //媒体资源
                    .addResourceLocations("classpath:/META-INF/resources/");
        } else {  //linux 和mac
            registry.addResourceHandler("/upload/**")
                    .addResourceLocations("file:" + uploadPath)   //媒体资源
                    .addResourceLocations("classpath:/META-INF/resources/");  //媒体资源;
        }
    }





//    @Bean
//    public MappingJackson2HttpMessageConverter getMappingJackson2HttpMessageConverter() {
//        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
//        //设置日期格式
//        ObjectMapper objectMapper = new ObjectMapper();
//        SimpleDateFormat smt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        objectMapper.setDateFormat(smt);
//        mappingJackson2HttpMessageConverter.setObjectMapper(objectMapper);
//        //设置中文编码格式
//        List<MediaType> list = new ArrayList<MediaType>();
//        list.add(MediaType.APPLICATION_JSON_UTF8);
//        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(list);
//        return mappingJackson2HttpMessageConverter;
//    }

}
