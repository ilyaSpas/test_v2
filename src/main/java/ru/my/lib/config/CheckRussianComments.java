package ru.my.lib.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class CheckRussianComments {
    private static final Pattern RUSSIAN_COMMENT_PATTERN = Pattern.compile("//.*[А-Яа-яЁё]|/\\*.*[А-Яа-яЁё].*\\*/");

    public static void main(String[] args) throws IOException {
        // Автоматически получаем путь к src/main/java
        Path sourcePath = Paths.get(System.getProperty("user.dir"), "src", "main", "java");

        try (Stream<Path> paths = Files.walk(sourcePath)) {
            boolean hasRussianComments = paths
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".java"))
                    .anyMatch(CheckRussianComments::hasRussianComments);

            if (hasRussianComments) {
                System.err.println("❌ Найдены русские комментарии в коде! Исправьте их перед слиянием.");
                System.exit(1);
            } else {
                System.out.println("✅ Русские комментарии не найдены. Всё ок!");
            }
        }
    }

    private static boolean hasRussianComments(Path filePath) {
        try {
            List<String> lines = Files.readAllLines(filePath);
            return lines.stream().anyMatch(line -> RUSSIAN_COMMENT_PATTERN.matcher(line).find());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
