package com.qingclass.squirrel.cms.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

/**
 * MD5加密
 */
public class MD5Util {

    /**
     * Encodes a string 2 MD5
     *
     * @param str String to encode
     * @return Encoded String
     * @throws NoSuchAlgorithmException
     */
    public static String crypt(String str) {
        if (str == null || str.length() == 0) {
            throw new IllegalArgumentException("String to encript cannot be null or zero length");
        }
        StringBuffer hexString = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] hash = md.digest();
            for (int i = 0; i < hash.length; i++) {
                if ((0xff & hash[i]) < 0x10) {
                    hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
                } else {
                    hexString.append(Integer.toHexString(0xFF & hash[i]));
                }
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hexString.toString();
    }


    public static void main(String[] args) {
        String s = "I have a pen , you have a pen";
        String tar = "";
        for(int i = 0 ; i < s.length() ; i ++){
            Character c = s.charAt(i);
            String temp = c.toString();
            if(Pattern.matches("\\W+",temp)){//匹配非字母数字
                if(i>0){
                    String a = tar.substring(tar.length()-1);
                    if(!a.equals(" ")){
                        tar += " ";
                    }
                }
            }else{
                tar += temp;
            }
        }

        System.out.println(tar);
    }

}
