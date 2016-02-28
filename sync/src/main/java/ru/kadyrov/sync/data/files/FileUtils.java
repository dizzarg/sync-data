package ru.kadyrov.sync.data.files;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FileUtils {

    public static InputStream inputStreamFromFile(String fileName) throws IOException {
        Path path = FileSystems.getDefault().getPath(fileName);
        byte[] bytes = Files.readAllBytes(path);
        return new ByteArrayInputStream(bytes);
    }

    public static void writeToFile(byte[] bytes, String fileName) throws IOException {
        Path path = FileSystems.getDefault().getPath(fileName);
        Files.createFile(path);
        Files.write(path, bytes, StandardOpenOption.WRITE);
    }

    public static boolean fileExists(String fileName) {
        Path path = FileSystems.getDefault().getPath(fileName);
        return Files.exists(path);
    }
}
