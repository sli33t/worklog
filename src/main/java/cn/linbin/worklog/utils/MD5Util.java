package cn.linbin.worklog.utils;

import java.security.MessageDigest;

/**
 * 自定义md5加密工具类, add by linbin
 */
public class MD5Util {

    /**
     * 加盐加密
     * @param str
     * @param salt
     * @return
     */
    public static String md5(String str, String salt){
        String result = salt + str + salt;
        return md5(result, true);
    }

    public static String md5(String str){
        return md5(str, true);
    }

    public static String md5(String str, boolean upper) {
        if (str == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(str.getBytes("utf-8"));
            byte[] digest = messageDigest.digest();

            int i;
            StringBuilder sb = new StringBuilder();
            for (int offset = 0; offset < digest.length; offset++) {
                i = digest[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    sb.append(0);
                sb.append(Integer.toHexString(i));//通过Integer.toHexString方法把值变为16进制
            }
            if (upper){
                return sb.toString().toUpperCase();
            }else {
                return sb.toString().toLowerCase();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        String md5 = MD5Util.md5("1","18801031984");
        System.out.println(md5);
    }

}
