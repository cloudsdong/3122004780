import Utils.StringUtils;
import Utils.FileUtils;

public class Main {
    public static void main(String[] args) {
        // 检查命令行参数数量
        if (args.length != 3) {
            System.out.println("请输入正确的参数数量：原文文件、抄袭文件和答案输出文件。");
            return;
        }

        try {
            // 使用 FileUtils 读取原文和抄袭文本，移除前后空白
            String originalText = FileUtils.readFile(args[0]).trim();
            String plagiarizedText = FileUtils.readFile(args[1]).trim();

            // 调用工具类计算相似度
            double similarity = StringUtils.calculateSimilarity(originalText, plagiarizedText);

            // 使用 FileUtils 将结果写入答案文件，保留两位小数
            FileUtils.writeFile(args[2], String.format("%.2f", similarity));
        } catch (Exception e) {
            // 捕获并处理异常，输出错误信息
            System.out.println("处理文件时发生错误：" + e.getMessage());
        }
    }
}
