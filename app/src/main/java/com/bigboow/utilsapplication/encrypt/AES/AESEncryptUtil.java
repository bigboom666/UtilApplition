package com.bigboow.utilsapplication.encrypt.AES;

import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.text.TextUtils;
import android.util.Pair;

import com.bigboow.utilsapplication.utils.LogUtil;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableEntryException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class AESEncryptUtil {

    public static final int SECRET_KEY_LENGTH = 128;
    public static final String ALGORITHM = "AES/CTR/NoPadding";
    public static final String AES_CTR_ALIAS = "aes_ctr_alias";
    public static final String ANDROID_KEY_STORE = "AndroidKeyStore";

    /**
     * 使用AES方式进行加密
     *
     * @return
     */
    public static Pair<byte[], byte[]> encrypt(byte[] rawData) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, NoSuchProviderException, InvalidAlgorithmParameterException {
        if (rawData == null) {
            LogUtil.d("rawData is null! ");
            return null;
        }
        final Cipher cipher = Cipher.getInstance(ALGORITHM);
        SecretKey secretKey = genSecretKey(AES_CTR_ALIAS);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] iv = cipher.getIV();
        byte[] encryptedData = cipher.doFinal(rawData);

        LogUtil.d("iv: " + Arrays.toString(iv));
        LogUtil.d("encryptedData: " + Arrays.toString(encryptedData));
        return new Pair<>(encryptedData, iv);
    }

    public static byte[] decryptData(byte[] encryptedData, byte[] iv) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnrecoverableEntryException, KeyStoreException {
        if (encryptedData == null || iv == null) {
            LogUtil.d("encryptedData||iv is null! ");
            return null;
        }
        final Cipher cipher = Cipher.getInstance(ALGORITHM);
        final IvParameterSpec spec = new IvParameterSpec(iv);
        SecretKey secretKey = KeyStoreUtil.getSecretKey(AES_CTR_ALIAS);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, spec);
        byte[] decryptedData = cipher.doFinal(encryptedData);
        return decryptedData;
    }


    //generate SecretKey and save in keystore, we can get SecretKey by alias
    private static SecretKey genSecretKey(final String alias) throws NoSuchProviderException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        if (TextUtils.isEmpty(alias)) {
            LogUtil.d("alias is null! ");
            return null;
        }
        final KeyGenerator keyGenerator = KeyGenerator
                .getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEY_STORE);
        keyGenerator.init(new KeyGenParameterSpec.Builder(alias,
                KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                .setBlockModes(KeyProperties.BLOCK_MODE_CTR)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .setKeySize(SECRET_KEY_LENGTH)
                .build());
        return keyGenerator.generateKey();
    }


}
