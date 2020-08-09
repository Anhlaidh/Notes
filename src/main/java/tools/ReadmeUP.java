package tools;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.Comparator;

/**
 * @Description:
 * @author: Anhlaidh
 * @date: 2020-08-09 17:29
 */
public class ReadmeUP {
    private static String url = "https://github.com/Anhlaidh/Notes/blob/master/src/main/java/notes/";

    public static void run()  {
        File dir = new File("./src/main/java/notes");
        File[] files = dir.listFiles();
        StringBuffer buffer = getBuff(files);
//        StringBuffer buffer = new StringBuffer();
        output(buffer);
//        read();

    }

    private static void read() {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("README.md")))) {
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private static void output(StringBuffer buffer) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("README.md")))) {
            bufferedWriter.write(buffer.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static StringBuffer getBuff(File[] files) {
        StringBuffer buffer = new StringBuffer();
        //一级标题
        buffer.append("#").append(" Notes").append("\n");

        for (File file : files) {
            if (file.isHidden()) continue;
            if (file.isDirectory()) {
                String tag = file.getName();
                if ('.' == tag.charAt(0)) continue;
                //二级标题
                buffer.append("##").append(" ").append(tag).append("\n");
                File[] children = file.listFiles();
                Arrays.sort(children, new Comparator<File>() {
                    @Override
                    public int compare(File file, File t1) {
                        BasicFileAttributes a = null;
                        BasicFileAttributes b = null;

                        try {
                            Path path = file.toPath();
                            a = Files.readAttributes(path, BasicFileAttributes.class);
                            path = t1.toPath();
                            b = Files.readAttributes(path, BasicFileAttributes.class);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (b.creationTime().toMillis() - a.creationTime().toMillis() > 0) {
                            return -1;
                        }
                        return 1;
                    }
                });
                for (File child : children) {

                    String[] split = child.getName().split("\\.");
                    if (split.length > 1 && "md".equals(split[1])) {
                        String name = split[0];
                        buffer.append("- ").append("[" + name + "]").append("(" + url + "/" + tag + "/" + name + ".md" + ")\n");
                    }
                }
            }
        }
        return buffer;
    }
}

