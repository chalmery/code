package top.yangcc;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");

        List<String> strs = new ArrayList<>();
        strs.add("a");
        System.out.println(JSON.toJSONString(strs));
    }
}
