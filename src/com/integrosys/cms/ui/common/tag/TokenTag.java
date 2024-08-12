package com.integrosys.cms.ui.common.tag;

import java.util.Random;



public class TokenTag {

    private static final Random token = new Random();
    private static final TokenProcessor processor = TokenProcessor.getInstance();
    private static byte[]  random = new byte[16];
    public static String generateToken() {
        token.nextBytes(random);
        String key = processor.toHex(random);
        return key ;
    }

}
