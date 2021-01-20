package com.bigboow.utilsapplication.encrypt.AES;

import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;

import javax.crypto.SecretKey;

import static com.bigboow.utilsapplication.encrypt.AES.AESEncryptUtil.ANDROID_KEY_STORE;

public class KeyStoreUtil {
    private  static  KeyStore keyStore;

    public static void initKeyStore() {
        try {
            keyStore = KeyStore.getInstance(ANDROID_KEY_STORE);
            keyStore.load(null);
        } catch (KeyStoreException | CertificateException | IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


    }

    public static SecretKey getSecretKey(final String alias) throws UnrecoverableEntryException, NoSuchAlgorithmException, KeyStoreException {
        if(keyStore == null){
            initKeyStore();
        }
        return ((KeyStore.SecretKeyEntry) keyStore.getEntry(alias, null)).getSecretKey();
    }

}
