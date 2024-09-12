import Utils.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class MainTest {

    private static final String ORIGINAL_FILE = "test_original.txt";
    private static final String PLAGIARIZED_FILE = "test_plagiarized.txt";
    private static final String ANSWER_FILE = "test_answer.txt";

    @Before
    public void setUp() throws IOException {
        // 在测试前清理之前的文件，准备干净的测试环境
        FileUtils.writeFile(ANSWER_FILE, "");
    }

    @After
    public void tearDown() {
        // 在测试结束后删除创建的测试文件
        new File(ORIGINAL_FILE).delete();
        new File(PLAGIARIZED_FILE).delete();
        new File(ANSWER_FILE).delete();
    }

    // 测试1：完全相同的文本
    @Test
    public void testIdenticalText() throws IOException {
        FileUtils.writeFile(ORIGINAL_FILE, "今天是星期天，天气晴，今天晚上我要去看电影。");
        FileUtils.writeFile(PLAGIARIZED_FILE, "今天是星期天，天气晴，今天晚上我要去看电影。");

        Main.main(new String[]{ORIGINAL_FILE, PLAGIARIZED_FILE, ANSWER_FILE});

        String result = FileUtils.readFile(ANSWER_FILE);
        assertEquals("100.00", result);
    }

    // 测试2：完全不同的文本
    @Test
    public void testCompletelyDifferentText() throws IOException {
        FileUtils.writeFile(ORIGINAL_FILE, "今天是星期天，天气晴。");
        FileUtils.writeFile(PLAGIARIZED_FILE, "我要去公园玩！");

        Main.main(new String[]{ORIGINAL_FILE, PLAGIARIZED_FILE, ANSWER_FILE});

        String result = FileUtils.readFile(ANSWER_FILE);
        assertEquals("0.00", result);
    }

    // 测试3：原文为空
    @Test
    public void testEmptyFile() throws IOException {
        FileUtils.writeFile(ORIGINAL_FILE, "");
        FileUtils.writeFile(PLAGIARIZED_FILE, "我今天要去公园玩。");

        Main.main(new String[]{ORIGINAL_FILE, PLAGIARIZED_FILE, ANSWER_FILE});

        String result = FileUtils.readFile(ANSWER_FILE);
        assertEquals("0.00", result);
    }

    // 测试4：抄袭文为空
    @Test
    public void testEmptyPlagiarizedFile() throws IOException {
        FileUtils.writeFile(ORIGINAL_FILE, "我今天要去公园玩。");
        FileUtils.writeFile(PLAGIARIZED_FILE, "");

        Main.main(new String[]{ORIGINAL_FILE, PLAGIARIZED_FILE, ANSWER_FILE});

        String result = FileUtils.readFile(ANSWER_FILE);
        assertEquals("0.00", result);
    }

    // 测试5：包含特殊字符
    @Test
    public void testSpecialCharacters() throws IOException {
        FileUtils.writeFile(ORIGINAL_FILE, "今天是星期天！天气很好。");
        FileUtils.writeFile(PLAGIARIZED_FILE, "今天是星期天？天气很好！");

        Main.main(new String[]{ORIGINAL_FILE, PLAGIARIZED_FILE, ANSWER_FILE});

        String result = FileUtils.readFile(ANSWER_FILE);
        assertEquals("83.33", result);
    }

    // 测试6：标点符号的差异
    @Test
    public void testPunctuationDifference() throws IOException {
        FileUtils.writeFile(ORIGINAL_FILE, "今天是星期天，天气晴，今天晚上我要去看电影。");
        FileUtils.writeFile(PLAGIARIZED_FILE, "今天是星期天 天气晴 今天晚上我要去看电影");

        Main.main(new String[]{ORIGINAL_FILE, PLAGIARIZED_FILE, ANSWER_FILE});

        String result = FileUtils.readFile(ANSWER_FILE);
        assertEquals("86.36", result);
    }

    // 测试7：多余的空格
    @Test
    public void testMultipleSpaces() throws IOException {
        FileUtils.writeFile(ORIGINAL_FILE, "今天是星期天，天气晴，今天晚上我要去看电影。");
        FileUtils.writeFile(PLAGIARIZED_FILE, "今天 是 星期天 ， 天气 晴 ， 今天 晚上 我要 去 看 电影 。");

        Main.main(new String[]{ORIGINAL_FILE, PLAGIARIZED_FILE, ANSWER_FILE});

        String result = FileUtils.readFile(ANSWER_FILE);
        assertEquals("100.00", result);
    }

    // 测试8：部分相似文本
    @Test
    public void testPartiallySimilarText() throws IOException {
        FileUtils.writeFile(ORIGINAL_FILE, "今天是星期天，天气晴，今天晚上我要去看电影。");
        FileUtils.writeFile(PLAGIARIZED_FILE, "今天是周天，天气晴朗，我晚上要去看电影。");

        Main.main(new String[]{ORIGINAL_FILE, PLAGIARIZED_FILE, ANSWER_FILE});

        String result = FileUtils.readFile(ANSWER_FILE);
        assertEquals("72.73", result);
    }

    // 测试9：大文件处理
    @Test
    public void testLargeFiles() throws IOException {
        StringBuilder originalText = new StringBuilder();
        StringBuilder plagiarizedText = new StringBuilder();

        // 生成大量文本数据
        for (int i = 0; i < 1000; i++) { // 调整循环次数以适应系统内存
            originalText.append("今天是星期天，天气晴，今天晚上我要去看电影。");
            plagiarizedText.append("今天是周天，天气晴朗，我晚上要去看电影。");
        }

        FileUtils.writeFile(ORIGINAL_FILE, originalText.toString());
        FileUtils.writeFile(PLAGIARIZED_FILE, plagiarizedText.toString());

        Main.main(new String[]{ORIGINAL_FILE, PLAGIARIZED_FILE, ANSWER_FILE});

        String result = FileUtils.readFile(ANSWER_FILE);
        assertEquals("72.73", result);
    }

    // 测试10：近乎完全相同但有小改动
    @Test
    public void testSlightlyModifiedText() throws IOException {
        FileUtils.writeFile(ORIGINAL_FILE, "今天是星期天，天气晴，今天晚上我要去看电影。");
        FileUtils.writeFile(PLAGIARIZED_FILE, "今天是星期天，天气晴，今天晚上我要去看电影了。");

        Main.main(new String[]{ORIGINAL_FILE, PLAGIARIZED_FILE, ANSWER_FILE});

        String result = FileUtils.readFile(ANSWER_FILE);
        assertEquals("95.65", result);
    }
}
