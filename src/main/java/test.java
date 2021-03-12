import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * @Description:
 * @author: Anhlaidh
 * @date: 2020-07-29 15:56
 */
public class test {

    public static void main(String[] args) {
        PriorityQueue<Integer> heap = new PriorityQueue<>();
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
