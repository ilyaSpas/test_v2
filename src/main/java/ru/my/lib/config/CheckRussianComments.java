package ru.my.lib.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CheckRussianComments {
    private static final Pattern RUSSIAN_COMMENT_PATTERN = Pattern.compile("//.*[А-Яа-яЁё]|/\\*.*[А-Яа-яЁё].*\\*/");

    public static void main(String[] args) throws IOException {
        Path sourcePath = Paths.get(System.getProperty("user.dir"), "src", "main", "java");

        try (Stream<Path> paths = Files.walk(sourcePath)) {
            boolean hasRussianComments = paths
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".java"))
                    .map(CheckRussianComments::checkFileForRussianComments)
                    .reduce(false, Boolean::logicalOr);

            if (hasRussianComments) {
                System.err.println("❌ Найдены русские комментарии в коде! Исправьте их перед слиянием.");
                System.exit(1);
            } else {
                System.out.println("✅ Русские комментарии не найдены. Всё ок!");
            }
        }
    }

    private static boolean checkFileForRussianComments(Path filePath) {
        try {
            List<String> lines = Files.readAllLines(filePath);
            boolean hasComments = IntStream.range(0, lines.size())
                    .filter(i -> RUSSIAN_COMMENT_PATTERN.matcher(lines.get(i)).find())
                    .filter(i -> !lines.get(i).contains("RUSSIAN_COMMENT_PATTERN")) // Исключаем строку с объявлением паттерна
                    .peek(i -> System.err.printf("❗ Найден русский комментарий в файле %s на строке %d: %s%n",
                            filePath.getFileName(), i + 1, lines.get(i).trim()))
                    .findFirst()
                    .isPresent();

            return hasComments;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
