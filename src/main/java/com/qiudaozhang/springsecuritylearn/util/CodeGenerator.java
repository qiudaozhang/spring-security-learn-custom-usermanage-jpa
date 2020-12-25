package com.qiudaozhang.springsecuritylearn.util;

import org.apache.commons.lang3.RandomStringUtils;
/**
 * @author 邱道长
 * 2020/12/25
 */
public class CodeGenerator {


    public static String codeNumber(int count) {
        return RandomStringUtils.randomNumeric(count);
    }

    public static String codeNumber() {
       return codeNumber(6);
    }
}
