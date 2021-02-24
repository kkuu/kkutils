package kk.kktools.sm;

import android.text.TextUtils;

import com.google.gson.Gson;

import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import okhttp3.RequestBody;
import okio.Buffer;

public class HttpSMSimpleTool {


    /***
     * 注意这两个不是一对的
     * 用于 sm2 加密 和 sm2 验签名
     * 客户端公钥，服务端私钥
      * @return
     */
    public static String getRsaServerPubKey(){
        return "";//SFDCCBHelper.getInstance().getRsaServerPubKey();
    }

    /***
     * 注意这两个不是一对的
     * 用于 sm2 解密 和 sm2 加签
     * 客户端私钥 服务端公钥
     * @return
     */
    public static String getRsaClientPriKey(){
        return "";//SFDCCBHelper.getInstance().getRsaClientPriKey();
    }


    /***
     *  用sm4(类似aes) 加密
     *
     * @param unEncryptedData
     * @return  数组0 =keyAndIvEncrypted  数组1=parametersEncrypt
     */
    public static String[] encryptedData(String unEncryptedData){
        byte[] aesKey = HttpSMUtils.getKeyForSM4();
        byte[] aesIv = HttpSMUtils.getIvForSM4();
        //sm4的密码用sm2加密
        String keyAndIvEncrypted = HttpSMUtils.getKeyAndIvEncryptedBySm2(aesKey, aesIv, getRsaServerPubKey());
        //用sm4加密数据
        String parametersEncrypt= HttpSMUtils.encryptBySM4(aesKey,aesIv,unEncryptedData);

        String [] result=new String[2];
        result[0]=keyAndIvEncrypted;
        result[1]=parametersEncrypt;
        return result;
    }

    public static String decyptData(String encyptedKey,String encyptedData){
        //用rsa 解密 sm4 用的密码
        String encryptedKeyAndIVStrDecrypt= HttpSMUtils.decryptBySM2WithPriKey(getRsaClientPriKey(),encyptedKey);
        Map map = new Gson().fromJson(encryptedKeyAndIVStrDecrypt, Map.class);
        byte[] keyBytes = ByteUtils.fromHexString(""+map.get("key"));
        byte[] ivBytes = ByteUtils.fromHexString(""+map.get("iv"));
        //用sm4解密数据
        String responseDecrypted = HttpSMUtils.decryptBySM4(keyBytes,ivBytes,encyptedData);
       return responseDecrypted;
    }





    /**
     * 请求加签使用
     * 将加密前的encryptedData json字串进行MD5加密,再将报文中sequenceNo、timestamp、Version
     * 一起以key=value的形式按照key名称进行升序排序然后以&作为连接符拼接成明文串a,
     * 将a字符串进行RSA私钥加密后得到signature签名字段。
     * @param paramsJson
     * @param sequenceNo
     * @param timestamp
     * @return
     */
    public static String signGenerate(String paramsJson, String clientID, String sequenceNo, String timestamp,String version) {
        Map<String, String> dataMap = new TreeMap<>();
        byte[] sm3Bytes = SM3Util.hash(paramsJson.getBytes(StandardCharsets.UTF_8));
        String sm3HexStr = HttpSMUtils.bytesToHexString(sm3Bytes);
        dataMap.put("clientId", clientID);
        dataMap.put("encryptedData", sm3HexStr);
        dataMap.put("sequenceNo", sequenceNo);
        dataMap.put("timestamp", timestamp);
        dataMap.put("version",version);
        return HttpSMUtils.signByPriKey(getRsaClientPriKey(),signGenerateSortStr(dataMap));
    }

    public static boolean signVerify(String paramsJson, String sequenceNo, long timestamp,String serverSign){
        byte[] sm3Hash = SM3Util.hash(paramsJson.getBytes(StandardCharsets.UTF_8));
        String sm3HexStr = HttpSMUtils.bytesToHexString(sm3Hash);

        Map<String, String> dataMap = new TreeMap<>();
        dataMap.put("encryptedData", sm3HexStr);
        dataMap.put("sequenceNo", sequenceNo);
        dataMap.put("timestamp", String.valueOf(timestamp));

        return HttpSMUtils.verifySign(getRsaServerPubKey(),signGenerateSortStr(dataMap),serverSign);
    }



    public static String signGenerateSortStr(Map<String, String> dataMap){
        StringBuilder srcStr = new StringBuilder();
        TreeMap<String, String> dataMapd = new TreeMap<>(dataMap);
        boolean isFirst = true;
        for (String key : dataMap.keySet()) {
            if (isFirst) {
                isFirst = false;
            } else {
                srcStr.append("&");
            }
            srcStr.append(key);
            srcStr.append("=");
            srcStr.append(dataMap.get(key));
        }
        return srcStr.toString();
    }


















    public static String bodyToString(final RequestBody request) {
        Buffer buffer = null;
        try {
            RequestBody copy = request;
            buffer = new Buffer();
            if (copy != null) copy.writeTo(buffer);
            else return "";
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        } finally {
            if (buffer != null) {
                buffer.close();
            }
        }
    }


    public static Map<String, String> bodyStrToMap(String bodyStr) {
        Map<String, String> maps = new HashMap<>();
        if (TextUtils.isEmpty(bodyStr)) {
            return maps;
        }
        String[] params = bodyStr.split("&");
        if (params != null && params.length > 0) {
            for (String keyAndValue : params) {
                //等号的值
                int sign = keyAndValue.indexOf("=");
                String key = keyAndValue.substring(0, sign);
                String value = keyAndValue.substring(sign + 1);
                maps.put(key, value);
            }
        } else {
            return maps;
        }
        return maps;
    }
}
