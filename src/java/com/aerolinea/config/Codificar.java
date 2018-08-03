package com.aerolinea.config;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Codificar implements PasswordEncoder{

    @Override
    public String encode(CharSequence rawPassword)  {
        MessageDigest mDigest;
        try {
            mDigest = MessageDigest.getInstance("SHA1");
        
        byte[] result = mDigest.digest(rawPassword.toString().getBytes());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
         
        return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Codificar.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encode(rawPassword).equals(encodedPassword);
    }
    
}

