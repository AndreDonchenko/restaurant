package ua.goit.andre.ee9;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andre on 11.09.2016.
 */
public class Util {

    public static List<String> getFilenames(String path) {
        List<String> result = new ArrayList();
        File dir = new File(path);
        if (dir.isDirectory()) {
            FileFilter fileFilter = new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    return pathname.isFile();
                }
            };
            for (File file : dir.listFiles(fileFilter)) {
                result.add(file.getName());
            }
        }
        return result;
    }
}
