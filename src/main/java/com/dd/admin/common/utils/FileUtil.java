package com.dd.admin.common.utils;

import net.coobird.thumbnailator.Thumbnails;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtil {

    /**
     * 压缩图片文件大小
     *
     * @param inputStream  输入图片流
     * @param outputStream 输出图片流
     * @param quality      压缩质量 (0.0 到 1.0，1.0表示最高质量)
     * @throws IOException
     */
    public static void compressImage(InputStream inputStream, OutputStream outputStream, float quality) throws IOException {
        // 使用Thumbnailator进行压缩
        Thumbnails.of(inputStream)
                .scale(1.0) // 保持原始尺寸
                .outputQuality(quality)
                .toOutputStream(outputStream);
    }
}
