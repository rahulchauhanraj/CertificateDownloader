package com.rah.cert;
import java.net.*;
import java.io.*;

public class XS2SSL {
    public static void main(String[] args) throws Exception {

        // Dynamic registration of JSSE provider
        java.security.Security.addProvider(
            new com.sun.net.ssl.internal.ssl.Provider());

        // Need to be set
        System.setProperty(
            "java.protocol.handler.pkgs",
            "com.sun.net.ssl.internal.www.protocol");

        // Install Authenticator
        //Authenticator.setDefault (new PasswordAuthenticator());

        URL url = new URL("https://your-UID+PWD-secured-site-URL-here");
        BufferedReader in = new BufferedReader(
            new InputStreamReader(
                url.openStream()));

        String inputLine;

        while ((inputLine = in.readLine()) != null)
            System.out.println(inputLine);

        in.close();
    }
}

