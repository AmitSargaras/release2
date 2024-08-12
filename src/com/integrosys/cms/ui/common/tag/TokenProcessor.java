package com.integrosys.cms.ui.common.tag;


import org.apache.struts.taglib.html.Constants;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by IntelliJ IDEA.
 * User: JITENDRA
 * Date: Mar 18, 2008
 * Time: 1:53:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class TokenProcessor {

    private static TokenProcessor instance = new TokenProcessor();

     public static final String TRANSACTION_TOKEN_KEY = "org.apache.struts.action.TOKEN";

    public static TokenProcessor getInstance() {
        return instance;
    }

    protected TokenProcessor() {
        super();
    }

    private long previous;

    public synchronized boolean isTokenValid(HttpServletRequest request) {
        return this.isTokenValid(request, false);
    }

    public synchronized boolean isTokenValid(HttpServletRequest request, boolean reset) {
        HttpSession session = request.getSession(false);
        if (session == null){
            return false;
        }
        String saved = (String) session.getAttribute(TRANSACTION_TOKEN_KEY);
        if (saved == null){
            return false;
        }
        if (reset){
            this.resetToken(request);
        }
        String token = request.getParameter(Constants.TOKEN_KEY);
        if (token == null){
            return false;
        }
        return saved.equals(token);
    }

    public synchronized void resetToken(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null){
            return;
        }
        session.removeAttribute(TRANSACTION_TOKEN_KEY);
    }


    public String toHex(byte[] buffer) {
        StringBuffer sb = new StringBuffer(buffer.length * 2);
        for (int i = 0; i < buffer.length; i++) {
            sb.append(Character.forDigit((buffer[i] & 240) >> 4, 16));
            sb.append(Character.forDigit(buffer[i] & 15, 16));
        }
        return sb.toString();
    }


    public synchronized String generateToken(HttpServletRequest request, TokenProcessor tokenProcessor) {
        HttpSession session = request.getSession();
        try {
            byte[] id = session.getId().getBytes();
            long current = System.currentTimeMillis();
            if (current == tokenProcessor.previous){
                current++;
            }
            tokenProcessor.previous = current;
            byte[] now = new Long(current).toString().getBytes();
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(id);
            md.update(now);
            return tokenProcessor.toHex(md.digest());
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
}
