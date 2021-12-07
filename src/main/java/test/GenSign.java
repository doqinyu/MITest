package test;

import org.apache.commons.codec.binary.Hex;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.TreeMap;

public class GenSign {
    // version_code=20200809&server_code=1&timestamp=1&r=IN&uId=900927b92cf5ac8d&count=10&l=en&group=1
    public static void main(String[] args) {
//        genCdnCallback();
//        genNoticeList();
//        genWidgetList();
        genParam();
    }


    private static void genParam() {
        TreeMap<String, String> treeMap = new TreeMap<>();
        treeMap.put("uId", "e53d1da20d27db19");
        treeMap.put("page", "0");
        treeMap.put("size", "10");


        treeMap.put("version_code", "100");
        treeMap.put("server_code", "1");
        //treeMap.put("r", "IN");
        treeMap.put("passport", "boss");
        //treeMap.put("l", "none");
        //treeMap.put("timestamp", "1");

        //treeMap.put("userId", "1f45f9706fe61c77");
        //treeMap.put("uuid", "1f45f9706fe61c77");
        //treeMap.put("money", "1");
        //treeMap.put("serialNo", "1_wx");
        //treeMap.put("noticeLanguage", "en");

        String sign = getSign(treeMap);
        System.out.println(sign);
    }



    private static String getSign(TreeMap<String, String> params) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> param : params.entrySet()) {
            sb.append(param.getKey()).append(":").append(param.getValue()).append("&");
        }

        StringBuilder builder = new StringBuilder(sb);

        builder.append("key=").append("3d5f1ffeadf58eb64ef57aef7e53a31e");
        String sign = null;
        try {
            sign = hexMD5(builder.toString());
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        sb.append("sign=").append(sign);
        return sb.toString();
    }

    private static String hexMD5(String value)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.reset();
        messageDigest.update(value.getBytes("utf-8"));
        byte[] digest = messageDigest.digest();
        return Hex.encodeHexString(digest);
    }
}
