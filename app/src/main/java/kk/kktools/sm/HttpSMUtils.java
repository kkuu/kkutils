//package kk.kktools.sm;
//
//import android.text.TextUtils;
//
//import com.google.gson.Gson;
//
//import org.bouncycastle.crypto.CryptoException;
//import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
//import org.bouncycastle.crypto.params.ECPublicKeyParameters;
//import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;
//
//import java.math.BigInteger;
//import java.nio.charset.StandardCharsets;
//import java.util.HashMap;
//
///**
// * @Description: 商密的工具类，用于http请求的加解密，加验签
// * aes-sm4;rsa-sm2,md5-sm3
// *
// */
//public class HttpSMUtils {
//    /**
//     * 私钥加签
//     * @param priKey
//     * @param srcStr 原始字串
//     * @return
//     */
//    public static String signByPriKey(String priKey,String srcStr){
//        if (TextUtils.isEmpty(srcStr)){
//            return "";
//        }
//        try {
//            ECPrivateKeyParameters ecPriKey = new ECPrivateKeyParameters(
//                    new BigInteger(priKey,16), SM2Util.DOMAIN_PARAMS);
//            byte[] srcByte = srcStr.getBytes(StandardCharsets.UTF_8);
//            byte[] signBytes = SM2Util.sign(ecPriKey, null, srcByte);
//            return HttpSMUtils.bytesToHexString(signBytes);
//        } catch (CryptoException e) {
//            e.printStackTrace();
//        }
//        return "";
//    }
//
//    /**
//     * 公钥验签
//     * @param pubKey
//     * @param srcStr 原始的字符串
//     * @param signStr 后端返回的签名之后的字符串
//     * @return
//     */
//    public static boolean verifySign(String pubKey,String srcStr,String signStr){
//        if (TextUtils.isEmpty(pubKey)||TextUtils.isEmpty(srcStr)){
//            return false;
//        }
//        Integer length = pubKey.length();
//        String pubKey1 = pubKey.substring(0,length/2);
//        String pubKey2 = pubKey.substring(length/2);
//        ECPublicKeyParameters ecPubKey = BCECUtil.createECPublicKeyParameters(pubKey1, pubKey2, SM2Util.CURVE, SM2Util.DOMAIN_PARAMS);
//        byte[] srcBytes = srcStr.getBytes(StandardCharsets.UTF_8);
//        byte[] signBytes = ByteUtils.fromHexString(signStr);
//        return SM2Util.verify(ecPubKey, null, srcBytes, signBytes);
//    }
//
//    /**
//     * sm4 公钥加密
//     * @param pubKey
//     * @param srcStr
//     * @return
//     */
//    public static String encryptBySM2WithPubKey(String pubKey,String srcStr){
//        if (TextUtils.isEmpty(pubKey)||TextUtils.isEmpty(srcStr)){
//            return "";
//        }
//        try {
//            Integer length = pubKey.length();
//            String pubKey1 = pubKey.substring(0,length/2);
//            String pubKey2 = pubKey.substring(length/2);
//            ECPublicKeyParameters ecPubKey = BCECUtil.createECPublicKeyParameters(pubKey1, pubKey2, SM2Util.CURVE, SM2Util.DOMAIN_PARAMS);
//            byte[] encrypted = SM2Util.encrypt(ecPubKey,srcStr.getBytes(StandardCharsets.UTF_8));
//            return HttpSMUtils.bytesToHexString(encrypted);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "";
//        }
//    }
//
//    /**
//     * sm2 私钥解密，默认原始字符串是经过16进制编码后的字符串
//     * @param priKey
//     * @param srcStr
//     * @return
//     */
//    public static String decryptBySM2WithPriKey(String priKey,String srcStr){
//        try {
//            //16进制解码
//            byte[] bytes = ByteUtils.fromHexString(srcStr);
//            ECPrivateKeyParameters ecPriKey = new ECPrivateKeyParameters(new BigInteger(priKey,16), SM2Util.DOMAIN_PARAMS);
//            byte[] decryptedBytes= SM2Util.decrypt(ecPriKey,bytes);
//            return new String(decryptedBytes,StandardCharsets.UTF_8);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "";
//        }
//    }
//
//    /**
//     * SM4加密
//     * @param key
//     * @param iv
//     * @param scrStr
//     * @return
//     */
//    public static String encryptBySM4(byte[] key,byte[]iv,String scrStr){
//        if (TextUtils.isEmpty(scrStr)){
//            return "";
//        }
//        try {
//            byte[] srcByte = scrStr.getBytes(StandardCharsets.UTF_8);
//            byte[] encrypted = SM4Util.encrypt_CBC_Padding(key,iv,srcByte);
//            return HttpSMUtils.bytesToHexString(encrypted);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "";
//    }
//
//    /**
//     * SM4解密
//     * @param key
//     * @param iv
//     * @param srcStr
//     * @return
//     */
//    public static String decryptBySM4(byte[] key, byte[]iv, String srcStr){
//        if (TextUtils.isEmpty(srcStr)){
//            return "";
//        }
//        try {
//            byte[] scrByte = ByteUtils.fromHexString(srcStr);
//            byte[] decryptedByte = SM4Util.decrypt_CBC_Padding(key,iv,scrByte);
//            return new String(decryptedByte,StandardCharsets.UTF_8);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "";
//    }
//
//    /**
//     * key 和 iv 组成的json字串 SM2 公钥加密返回
//     * @param sm4Key 16进制编码
//     * @param iv 16进制编码
//     * @param pubKey
//     * @return
//     */
//    public static String getKeyAndIvEncryptedBySm2(byte[]sm4Key,byte[]iv,String pubKey){
//        try {
//            String sm4KeyStr = HttpSMUtils.bytesToHexString(sm4Key);
//            String sm4IvStr = HttpSMUtils.bytesToHexString(iv);
//            HashMap<String,String>map=new HashMap<>();
//            map.put("key",sm4KeyStr);
//            map.put("iv",sm4IvStr);
//            String keyAndIvJson = new Gson().toJson(map);
//            return encryptBySM2WithPubKey(pubKey,keyAndIvJson);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "";
//        }
//    }
//
//    /**
//     * 生成sm4使用的key，默认长度为16位
//     * @return
//     */
//    public static byte[] getKeyForSM4(){
//        try {
//            return SM4Util.generateKey();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return new byte[]{};
//    }
//
//    /**
//     * 生成sm4使用的iv，默认长度为16位
//     * @return
//     */
//    public static byte[] getIvForSM4(){
//        try {
//            return SM4Util.generateKey();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return new byte[]{};
//    }
//
////    /**
////     * 解密失败，则直接返回原文。
////     * @param response
////     * @return
////     */
////    public static String decryptResponse(String response){
////        try {
////            UnDecryptedResponseEntity unDecryptedResponseEntity = new Gson().fromJson(response, UnDecryptedResponseEntity.class);
////            String priKey = BangCryptUtils.sensitiveDeByAES(Const.SensitiveBangData.HTTP_SM2_CLIENT_PRI_KEY);
////            String keyDecrypted = HttpSMUtils.decryptBySM2WithPriKey(priKey,unDecryptedResponseEntity.getEncryptedKey());
////            KeyAndIv keyAndIv = new Gson().fromJson(keyDecrypted, KeyAndIv.class);
////            byte[] keyBytes = ByteUtils.fromHexString(keyAndIv.getKey());
////            byte[] ivBytes = ByteUtils.fromHexString(keyAndIv.getIv());
////            String responseDecrypted = HttpSMUtils.decryptBySM4(keyBytes,ivBytes,unDecryptedResponseEntity.getEncryptedData());
////            if (TextUtils.isEmpty(responseDecrypted)){
////                return response;
////            }
////            return responseDecrypted;
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////        //
////        return response;
////    }
//
//    public static String bytesToHexString(byte[] src){
//        StringBuilder stringBuilder = new StringBuilder("");
//        if (src == null || src.length <= 0) {
//            return "";
//        }
//        for (int i = 0; i < src.length; i++) {
//            int v = src[i] & 0xFF;
//            String hv = Integer.toHexString(v);
//            if (hv.length() < 2) {
//                stringBuilder.append(0);
//            }
//            stringBuilder.append(hv);
//        }
//        return stringBuilder.toString();
//    }
//}
