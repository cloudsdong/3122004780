package Utils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.nio.file.NoSuchFileException;

public class FileUtils {

    // 读取文件内容，返回字符串
    public static String readFile(String filePath) throws IOException {
        try {
            // 检查文件是否存在
            if (!Files.exists(Paths.get(filePath))) {
                throw new NoSuchFileException(filePath + " 文件不存在");
            }
            // 读取文件内容并返回字符串
            return new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            // 捕获文件读取时的 IO 异常
            throw new IOException("读取文件时出错：" + e.getMessage(), e);
        }
    }

    // 将字符串写入文件
    public static void writeFile(String filePath, String content) throws IOException {
        try {
            // 将内容写入文件
            Files.write(Paths.get(filePath), content.getBytes());
        } catch (IOException e) {
            // 捕获文件写入时的 IO 异常
            throw new IOException("写入文件时出错：" + e.getMessage(), e);
        }
    }
}
