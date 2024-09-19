package com.counseling.cms.utility;

import java.util.Base64;

public class Base64UrlSafe {
	public static String encode(byte[] input) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(input);
    }

    public static byte[] decode(String input) {
        return Base64.getUrlDecoder().decode(input);
    }
}
