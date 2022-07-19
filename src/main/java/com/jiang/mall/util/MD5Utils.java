package com.jiang.mall.util;

import com.jiang.mall.common.Constant;
import org.apache.tomcat.util.codec.binary.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/*
 描述: md5工具
 */
public class MD5Utils {
    public static String getMD5Str(String strValue) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        return Base64.encodeBase64String(md5.digest(md5.digest((strValue+ Constant.SALT).getBytes())));
    }

    public static void main(String[] argument) {
        try {
            String password = getMD5Str("18176283201");
            System.out.println(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }
}
