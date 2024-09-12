package Utils;

import java.util.Arrays;

public class StringUtils {

    // 去除标点和特殊字符，保留字母、数字和中文字符
    private static String preprocessText(String text) {
        return text.replaceAll("[\\p{Punct}\\s]+", "").toLowerCase();
    }

    // 计算两个字符串的相似度，返回百分比值
    public static double calculateSimilarity(String original, String plagiarized) {
        // 对原文和抄袭文本进行预处理
        String processedOriginal = preprocessText(original);
        String processedPlagiarized = preprocessText(plagiarized);

        // 如果两个预处理后的文本为空，返回相似度为 100%
        if (processedOriginal.isEmpty() && processedPlagiarized.isEmpty()) {
            return 100.00;
        }

        // 使用 Levenshtein 距离计算相似度
        int maxLength = Math.max(processedOriginal.length(), processedPlagiarized.length());
        if (maxLength == 0) {
            return 100.00;
        }

        int distance = levenshteinDistance(processedOriginal, processedPlagiarized);
        double similarity = (1 - (double) distance / maxLength) * 100;

        // 保留两位小数
        return Math.round(similarity * 100.0) / 100.0;
    }

    // 使用动态规划计算 Levenshtein 编辑距离
    public static int levenshteinDistance(String s1, String s2) {
        int len1 = s1.length();
        int len2 = s2.length();

        // 创建 DP 数组
        int[][] dp = new int[len1 + 1][len2 + 1];

        // 初始化
        for (int i = 0; i <= len1; i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= len2; j++) {
            dp[0][j] = j;
        }

        // 动态规划计算编辑距离
        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                int cost = (s1.charAt(i - 1) == s2.charAt(j - 1)) ? 0 : 1;
                dp[i][j] = Math.min(Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1), dp[i - 1][j - 1] + cost);
            }
        }

        return dp[len1][len2];
    }
}
