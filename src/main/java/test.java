import java.util.Arrays;

/**
 * @Description:
 * @author: Anhlaidh
 * @date: 2020-07-29 15:56
 */
public class test {

    public static void main(String[] args) {
        String a = new String("aaa");
        StringBuffer b = new StringBuffer(a);
        b.append(a).append(a);
        Integer c = 100;
//        Integer.toBinaryString();
        int i = 0;
        while (true) {
            i++;
            b = b.append(i);
            System.out.println(b.toString());
        }

    }
}
