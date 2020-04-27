package tools;

import com.github.houbb.markdown.toc.core.impl.AtxMarkdownToc;

/**
 * @Description:
 * @author: Anhlaidh
 * @date: 2020-04-17 22:40
 */
public class title {
    public static void main(String[] args) {
        AtxMarkdownToc.newInstance().genTocFile("G:\\Notes\\src\\main\\java\\Java_Advanced.md");
    }
}
