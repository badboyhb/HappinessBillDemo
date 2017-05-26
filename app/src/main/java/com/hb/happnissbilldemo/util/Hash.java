package com.hb.happnissbilldemo.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by HB on 2017/5/25.
 */

public class Hash {

    public static String getHash(String str) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA1");
            digest.reset();
            byte[] data = digest.digest(str.getBytes());
            return String.format("%0" + (data.length * 2) + "X", new BigInteger(1, data));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}