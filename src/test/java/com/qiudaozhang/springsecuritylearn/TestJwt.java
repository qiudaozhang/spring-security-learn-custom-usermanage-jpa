package com.qiudaozhang.springsecuritylearn;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;

import java.security.Key;
import java.util.Date;


/**
 * @author 邱道长
 * 2020/12/25
 */
public class TestJwt {


    @Test
    public void m1() {

//        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
///
//        String jws = Jwts.builder().setSubject("Joe").signWith(key).compact();

        String token = Jwts.builder()
                .setSubject("791915576020873216")
                .setExpiration(new Date(System.currentTimeMillis() + 10000))
                .signWith(SignatureAlgorithm.HS512, "pKUdmdtI6Sh3JUwr7fbX1Bku1EjEFqFgnic8jPTVWUejAu0fXAv0SL5zvjOI")
                .compact();
        System.out.println(token);
        // eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI3OTE5MTU1NzYwMjA4NzMyMTYiLCJleHAiOjE2MDg4ODQ5MTF9.FXiL8JolwyS3sqE-u4cZJ9HIcaH06z2GDr2N7hOZChEMG_NKH95tFZCa4Tdf5uPINMKYbwJ42bzUYIyWA6LxSg
        // eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI3OTE5MTU1NzYwMjA4NzMyMTYiLCJleHAiOjE2MDg4ODQ5MjF9.8y8Qm4FMLb8bMybwawSY7A5yggzaH5yjvGba8gUN5JsQ8sh-zv-h0MLtFs2yye_HYdVMrE4jXtZkQCFlbers7w
    }
}
