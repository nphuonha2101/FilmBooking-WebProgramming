package com.filmbooking.ultils;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Objects;

public class FileUtils {
    private File dir;

    public FileUtils(String directoryPath) {
        this.dir = new File(directoryPath);
    }

    private int countDuplicateFile(String fileName) {
        int result = 0;

        if (Objects.requireNonNull(dir.listFiles()).length > 0)
            for (File fileInDirectory : dir.listFiles()) {
                if (fileInDirectory.getName().equalsIgnoreCase(fileName)) result++;
            }
        return result;
    }

    public boolean isValidFile(String fileName) {
        String[] validExtension = {".jpg", ".png"};
        FilenameFilter extensionFilter = new ExtensionFilter(validExtension);
        return extensionFilter.accept(dir, fileName);
    }

    public String handlesFileName(String fileName) {
        int duplicateFiles = countDuplicateFile(fileName);
        if (duplicateFiles > 0) {
            fileName = "Copy " + duplicateFiles + "-" + fileName;
        }
        return fileName;
    }
}
