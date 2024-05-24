package com.steelboys.vulnerablebank.database;

import static javax.crypto.Cipher.DECRYPT_MODE;

import android.content.Context;

import com.steelboys.vulnerablebank.R;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.Cipher;

public class SomeSpiceEncryption {

    private static String encryptHelper(Context ctx) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(ctx.getString(R.string.app_encryption_key).getBytes(), "AES/ECB/NoPadding");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(DECRYPT_MODE, secretKeySpec);
        return Arrays.toString(cipher.doFinal(ctx.getString(R.string.app_root_phone_magic).getBytes()));
    }

    public static String getDecryptedFlag(Context ctx) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        return encryptHelper(ctx);
    }
}
