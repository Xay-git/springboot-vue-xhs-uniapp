package com.dd.admin.common.utils;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Objects;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;
import org.lionsoul.ip2region.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

/**
 * 根据ip离线查询地址
 *
 * @author ruoyi
 */
public class RegionUtil {
    private static final Logger log = LoggerFactory.getLogger(RegionUtil.class);

    private static final String JAVA_TEMP_DIR = "java.io.tmpdir";

    static DbConfig config = null;
    static DbSearcher searcher = null;

    public static void main(String[] args) {
        getRegion("103.85.74.92");
    }

    // 初始化IP库
    static {
        try {
            // 因为jar无法读取文件,复制创建临时文件
            String dbPath = Objects.requireNonNull(RegionUtil.class.getResource("/ip2region/ip2region.db")).getPath();
            File file = new File(dbPath);
            if (!file.exists()) {
                String tmpDir = System.getProperties().getProperty(JAVA_TEMP_DIR);
                dbPath = tmpDir + "ip2region.db";
                file = new File(dbPath);
                ClassPathResource cpr = new ClassPathResource("ip2region" + File.separator + "ip2region.db");
                InputStream resourceAsStream = cpr.getInputStream();
                FileUtils.copyInputStreamToFile(resourceAsStream, file);
            }
            config = new DbConfig();
            searcher = new DbSearcher(config, dbPath);
            log.info("bean [{}]", config);
            log.info("bean [{}]", searcher);
        } catch (Exception e) {
            log.error("init ip region error:{}", e.toString());
        }
    }

    /**
     * 解析IP
     *
     * @param ip
     * @return
     */
    public static String getRegion(String ip) {
        try {
            // db
            if (searcher == null || StringUtils.isEmpty(ip)) {
                log.error("DbSearcher is null");
                return StringUtils.EMPTY;
            }
            long startTime = System.currentTimeMillis();
            // 查询算法
            Method method = searcher.getClass().getMethod("memorySearch", String.class);

            DataBlock dataBlock = null;
            if (!Util.isIpAddress(ip)) {
                log.warn("warning: Invalid ip address");
            }
            dataBlock = (DataBlock) method.invoke(searcher, ip);
            String result = dataBlock.getRegion();
            long endTime = System.currentTimeMillis();
            log.debug("region use time[{}] result[{}]", endTime - startTime, result);
            return result;

        } catch (Exception e) {
            log.error("error:{}", e.toString());
        }
        return StringUtils.EMPTY;
    }

}
