package com.example.admin;

import org.apache.shiro.crypto.hash.SimpleHash;

public class MD5Test {
    public static void main(String[] args) {
        String hashAlgorithmName = "MD5";
        String credentials = "123456";
        int hashIterations = 1;
        String salt = "mar%#$@";
        Object obj = new SimpleHash(hashAlgorithmName, credentials,salt, hashIterations);
        System.out.println(obj);
    }
}
