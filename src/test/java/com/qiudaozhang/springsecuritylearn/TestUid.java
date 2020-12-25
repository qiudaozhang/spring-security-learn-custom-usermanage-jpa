package com.qiudaozhang.springsecuritylearn;

import com.qiudaozhang.springsecuritylearn.commom.SequenceGenerator;
import org.junit.jupiter.api.Test;

/**
 * @author 邱道长
 * 2020/12/25
 */
public class TestUid {

    @Test
    public void m1() {
        SequenceGenerator sg = new SequenceGenerator();
        long id = sg.nextId();
        System.out.println(id);
    }
}
