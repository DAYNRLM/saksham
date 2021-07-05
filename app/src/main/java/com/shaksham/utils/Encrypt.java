package com.shaksham.utils;

import android.os.Build;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Encrypt {
    public static byte[] getSecrectKey(byte[] seed) {
        byte[] raw = null;
        try{

           // SecretKey key = new SecretKeySpec(keyBytes, "AES");

            KeyGenerator kgen = KeyGenerator.getInstance("AES");
           // KeyGenerator kgen = KeyGenerator.getInstance("PBKDF2WithHmacSHA1");
          //  KeyGenerator kgen = KeyGenerator.getInstance("AES/CBC/PKCS5Padding");
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
           // SecureRandom sr = SecureRandom.getInstance("PBKDF2WithHmacSHA1");
            sr.setSeed(seed);
            kgen.init(128, sr); // 192 and 256 bits may not be available
            SecretKey skey = kgen.generateKey();
            raw = skey.getEncoded();
        }catch(Exception e){
            e.printStackTrace();
        }
        return raw;
    }

    public static String encoderfun(byte[] decval) {
        String conVal= android.util.Base64.encodeToString(decval, android.util.Base64.DEFAULT);
        return conVal;
    }

    public static String encrypt(String actualValue) throws Exception {
        byte[] dataToBeEncrypt = actualValue.getBytes("UTF-8");
        byte[] key = getSecrectKey("78965412".getBytes("UTF-8"));

        String sKey = encoderfun(key);


        SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");



        Cipher cipher = Cipher.getInstance("AES");
        /*SecretKeySpec skeySpec = new SecretKeySpec(key, "AES/CBC/PKCS5Padding");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
*/
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = new byte[0];
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
           // encrypted = Base64.getEncoder().encode(cipher.doFinal(dataToBeEncrypt));
            encrypted = cipher.doFinal(dataToBeEncrypt);
        }
        String encryptedValue = new String(encrypted);
        //Base64.getEncoder().encode(arg0);
        return encryptedValue;
    }


    public static String decryptiondata(String encryptedValue) throws Exception {
        byte[] key = getSecrectKey("78965412".getBytes("UTF-8"));
       /* SecretKeySpec skeySpec = new SecretKeySpec(key,  "AES/CBC/PKCS5Padding");
        Cipher cipher = Cipher.getInstance( "AES/CBC/PKCS5Padding");*/

        SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] encryptedData = encryptedValue.getBytes("UTF-8");
        byte[] decrypted = new byte[0];
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
           // decrypted = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
            decrypted=cipher.doFinal(encryptedData);
        }
        String decryptedValue = new String(decrypted);
        return decryptedValue;
    }



    public static String encode(String actualValue) throws Exception{
        String encodedValue = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            encodedValue = new String(Base64.getEncoder().encode(actualValue.getBytes()));
        }
        return encodedValue;
    }
    public static String decode(String encodedValue) throws Exception{
        String actualValue = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            actualValue = new String(Base64.getDecoder().decode(encodedValue.getBytes()));
        }
        return actualValue;
    }




    public static String encrypt1(String clearText) {
        byte[] encryptedText = null;
        try {
            byte[] keyData =getSecrectKey("78965412".getBytes("UTF-8"));
            SecretKey ks = new SecretKeySpec(keyData, "AES");
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.ENCRYPT_MODE, ks);
            encryptedText = c.doFinal(clearText.getBytes("UTF-8"));
            return android.util.Base64.encodeToString(encryptedText, android.util.Base64.NO_WRAP);
        } catch (Exception e) {
            AppUtility.getInstance().showLog("Exception"+e,Encrypt.class);
            return null;
        }
    }

    public static String decrypt1 (String encryptedText) {
        byte[] clearText = null;
        byte[] clearText2 = encryptedText.getBytes();
        try {
            byte[] keyData = getSecrectKey("78965412".getBytes("UTF-8"));
            SecretKey ks = new SecretKeySpec(keyData, "AES");
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.DECRYPT_MODE, ks);
            clearText = c.doFinal(android.util.Base64.decode(encryptedText, android.util.Base64.NO_WRAP));

           // clearText = c.doFinal(Base64.getDecoder().decode(clearText2));


            return new String(clearText, "UTF-8");
        } catch (Exception e) {
            AppUtility.getInstance().showLog("Exception"+e,Encrypt.class);
            return null;
        }
    }

}
