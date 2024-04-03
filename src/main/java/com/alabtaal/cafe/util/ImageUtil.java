package com.alabtaal.cafe.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ImageUtil {
    public static void saveImage(File imageFile) throws IOException {
        try {
            String projectDirectory = System.getProperty("user.dir");
            Path targetPath = Paths.get(projectDirectory, "src", "main", "resources", imageFile.getName());
            Files.copy(imageFile.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
             e.printStackTrace();
            throw e;
        }
    }
}
