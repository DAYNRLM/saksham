package com.shaksham.utils;






import android.util.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;


import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Encryptor {


 /*   public static byte[] getSecrectKey(byte[] seed) {*/
/*

        KeyGenerator keyGenerator = null;
        SecretKey secretKey;
        byte[] secretKeyen;
        String strSecretKey;
        byte[] IV = new byte[16];
        byte[] cipherText;
        SecureRandom random;


        try {
            keyGenerator = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        keyGenerator.init(256);// init yöntemiyle oluşturulan KeyGenerator örneği başlatılır.Burada 256bit değer kullanıldı.
        secretKey = keyGenerator.generateKey();//Kurulum tamamlandıktan sonra generateKey() ile anahtar üretilir.
        secretKeyen=secretKey.getEncoded();
        strSecretKey = encoderfun(secretKeyen);


        random = new SecureRandom();
        random.nextBytes(IV);

        try {
            cipherText = Encrypt.encrypt(etOrjText.getText().toString().trim().getBytes(), secretKey, IV);

            String sonuc = encoderfun(cipherText);
            tvSonuc.setText(sonuc);


            String tvIV = encoderfun(IV);
            tvAnahtar.setText(tvIV);

        } catch (Exception e) {
            e.printStackTrace();
        }

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
        String conVal= Base64.encodeToString(decval,Base64.DEFAULT);
        return conVal;
    }


    public static byte[] encrypt(byte[] plaintext, SecretKey key, byte[] IV) throws Exception
    {   */
/*Şifreleme sınıfı, gerçek şifreleme ve şifre çözme işlemlerini yapan sınıftır. Cipher sınıfı örneği, Cipher adını parametre olarak geçiren getInstance () yöntemi çağrılarak oluşturulur, biz AES/CBC/PKCS5Padding kullandık.*//*

        Cipher cipher = Cipher.getInstance("AES");
        */
/*SecretKeySpec, bayt verilerini Cipher sınıfının init () yöntemine aktarılmaya uygun bir gizli anahtara dönüştürme mekanizması sağlar.*//*

        SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "AES");
        */
/*ivParameterSpec, bir başlatma vektörü için bir sarıcıdır, IV, IvParameterSpec'in konfigüre edilme şeklindeki rastgeleliğini alır.*//*

        IvParameterSpec ivSpec = new IvParameterSpec(IV);
        */
/*Cipher örneği oluşturulduktan sonra, init () yöntemini çağırarak şifreli örneğini başlatmamız gerekir. 3 parametreyi init () yöntemine geçirmemiz gerekir.
         * Cipher.ENCRYPT_MODE
         * keySpec
         * ivSpec*//*

        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        byte[] cipherText = cipher.doFinal(plaintext);
        return cipherText;
    }

    public static String decrypt(byte[] cipherText, SecretKey key, byte[] IV)
    {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(IV);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            byte[] decryptedText = cipher.doFinal(cipherText);
            return new String(decryptedText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
*/

}
