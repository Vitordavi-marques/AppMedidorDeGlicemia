package com.appdavovo.models.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public abstract class HashHelper {

    private static final String ALGORITHM = "SHA-256";

    public static String checksum(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
            byte[] encodedhash = digest.digest(
                    input.getBytes(StandardCharsets.UTF_8));
            String result = bytesToString(encodedhash);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String bytesToString(byte[] encodedHash) {
        StringBuffer hexString = new StringBuffer();

        for (int i = 0; i < encodedHash.length; i++) {
            String hex = Integer.toHexString(0xff & encodedHash[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }

        return hexString.toString();
    }
}
