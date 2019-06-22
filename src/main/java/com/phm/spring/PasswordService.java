package com.phm.spring;

/**
 *
 * @author AnhNH Ham ma hoa password
 */
import java.security.MessageDigest;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;


public final class PasswordService {

    private static PasswordService instance;

    private PasswordService() {
    }

    public String encrypt(String plaintext) throws Exception {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1"); //step 2
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            md.update(plaintext.getBytes("UTF-8")); //step 3
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        byte raw[] = md.digest(); //step 4
        String hash = (new BASE64Encoder()).encode(raw); //step 5
        return hash; //step 6
    }

 
    public static synchronized PasswordService getInstance() //step 1
    {
        if (instance == null) {
            instance = new PasswordService();
        }
        return instance;
    }
    
//   public static void main(String[] strs) throws Exception{
//       System.out.println(" p " + PasswordService.getInstance().encrypt("111111"));
//   }   
}

