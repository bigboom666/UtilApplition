package com.bigboow.utilsapplication.encrypt.AES;

import android.util.Pair;

import com.bigboow.utilsapplication.HexUtil;
import com.bigboow.utilsapplication.LogUtil;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableEntryException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

//todo use Test to instead
public class AESTest {
    static Pair<byte[], byte[]> encryption = null;
    static String decryption = null;

    public static void encryptTest() {
        try {
            encryption = AESEncryptUtil.encrypt(HexUtil.strToByteArray("123456abxyz:!"));
            LogUtil.i("encryption, encryptedData: " + Arrays.toString(encryption.first) + "iv: " + Arrays.toString(encryption.second));
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | NoSuchProviderException | InvalidAlgorithmParameterException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            LogUtil.e("encryptException", e);
        }
    }

    public static void decryptTest() {
        if (encryption != null) {
            try {
                decryption = HexUtil.byteArrayToStr(AESEncryptUtil.decryptData(encryption.first, encryption.second));
                LogUtil.i("decryption: " + decryption);
            } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidAlgorithmParameterException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException | UnrecoverableEntryException | KeyStoreException | UnsupportedEncodingException e) {
                LogUtil.e("decryptException", e);
            }
        }
    }


}
