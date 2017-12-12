package com.rah.cert;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

class PasswordAuthenticator extends Authenticator {

    public PasswordAuthenticator() {
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        System.out.println("getPasswordAuthentication() called for https connection!!!");
        return new PasswordAuthentication("", "changeit".toCharArray());
    }
}
