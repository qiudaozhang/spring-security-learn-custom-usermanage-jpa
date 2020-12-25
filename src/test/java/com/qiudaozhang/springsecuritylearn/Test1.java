package com.qiudaozhang.springsecuritylearn;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 邱道长
 * 2020/12/25
 */
public class Test1 {

    @Test
    public void m1() {
        List<String> l = new ArrayList();
        l.add("a");
        l.add("b");
        l.add("c");
        String[] strings = l.toArray(new String[0]);
        m2(strings);
    }

    public void m2(String ... args) {
        for(String arg:args) {
            System.out.println(arg);
        }
    }
}
