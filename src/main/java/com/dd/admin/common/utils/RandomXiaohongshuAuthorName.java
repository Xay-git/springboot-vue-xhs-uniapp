package com.dd.admin.common.utils;

import java.util.Random;

public class RandomXiaohongshuAuthorName {

    // 增加更多风格各异的形容词
    private static final String[] ADJECTIVES =  {"甜系", "酷盖", "元气", "文艺", "清新", "时尚", "复古", "个性", "慵懒", "灵动","甜系", "萌趣", "清新", "元气", "梦幻", "文艺", "优雅", "酷盖", "潮流", "时尚", "复古", "治愈", "俏皮", "灵动", "简约", "精致", "浪漫", "神秘", "个性", "森系"};
    // 丰富名词列表
    private static final String[] NOUNS = {"喵星人", "星辰", "云朵", "花田", "森林", "梦境", "清风", "暖阳", "微光", "小确幸","小熊", "兔子", "云朵", "星辰", "花朵", "森林", "海风", "咖啡", "画笔", "月光", "糖果", "蛋糕", "风铃", "泡泡", "琉璃", "蝴蝶"};

    // 定义一些风格词汇数组

    public static void main(String[] args) {
        String authorName = generateAuthorName();
        System.out.println(authorName);
    }

    public static String generateAuthorName() {
        Random random = new Random();
        // 随机选择一个形容词
        int adjectiveIndex = random.nextInt(ADJECTIVES.length);
        String adjective = ADJECTIVES[adjectiveIndex];
        // 随机选择一个名词
        int nounIndex = random.nextInt(NOUNS.length);
        String noun = NOUNS[nounIndex];
        // 随机生成一个数字后缀
        int number = random.nextInt(100);

        return adjective + noun + number;
    }
}
