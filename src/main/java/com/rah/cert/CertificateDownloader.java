package com.rah.cert;

import java.io.File;
import java.io.FileOutputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.security.cert.Certificate;
import java.security.cert.CertificateExpiredException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HttpsURLConnection;

public class CertificateDownloader {

    public static void main (String[] args) throws Exception {
        String tempPath = System.getenv("TEMP");
        testConnectionTo(tempPath, "https://tenancy-stuf-stufrc.apm.aws-usw02-pr.predix.io", "tenant-cert");
        testConnectionTo(tempPath, "https://apm-asset-svc-rc.int-app.aws-usw02-pr.predix.io", "asset-cert");
        testConnectionTo(tempPath, "https://config-stuf-stufrc.apm.aws-usw02-pr.predix.io", "stuf-cert");
        testConnectionTo(tempPath, "https://apm-templates-svc-rc.int-app.aws-usw02-pr.predix.io", "template-cert");
        testConnectionTo(tempPath, "https://apm-alarms-svc-rc.int-app.aws-usw02-pr.predix.io", "alarm-cert");
        testConnectionTo(tempPath, "https://apm-blob-storage-svc-rc.int-app.aws-usw02-pr.predix.io", "blob-cert");
    }

    public static void testConnectionTo(String path, String aURL, String certName) throws Exception {
        String filePath = path + "\\" + certName;
        URL destinationURL = new URL(aURL);
        System.setProperty("https.proxyHost","http-proxy.corporate.ge.com");
        System.setProperty("https.proxyPort","80");
        System.setProperty("http.proxyHost","http-proxy.corporate.ge.com");
        System.setProperty("http.proxyPort","80");
        InetSocketAddress proxyInet = new InetSocketAddress("http-proxy.corporate.ge.com",80);
        Proxy proxy = new Proxy(Proxy.Type.HTTP, proxyInet);
        HttpsURLConnection conn = (HttpsURLConnection) destinationURL.openConnection(proxy);
        conn.connect();
        Certificate[] certs = conn.getServerCertificates();

        for (Certificate cert : certs) {
            if(cert instanceof X509Certificate) {
                FileOutputStream os = null;
                try {
                    ( (X509Certificate) cert).checkValidity();
                    os = new FileOutputStream(filePath);
                    os.write(cert.getEncoded());
                } catch(CertificateExpiredException cee) {
                    System.out.println("Certificate is expired");
                }finally {
                    if (os != null) {
                        os.close();
                    }
                }
            } else {
                System.err.println("Unknown certificate type: " + cert);
            }
        }

        String javaHome = System.getenv("JAVA_HOME");
        String deleteCommand = String.format("keytool.exe -delete -alias %s -storepass changeit -keystore \"%s\\jre\\lib\\security\\cacerts\"", certName, javaHome);
        Runtime.getRuntime().exec(deleteCommand).waitFor();

        String installCommand = String.format("keytool.exe -importcert -noprompt -trustcacerts -alias %s -storepass changeit -file %s -keystore \"%s\\jre\\lib\\security\\cacerts\"", certName, filePath, javaHome);
        Runtime.getRuntime().exec(installCommand).waitFor();
        System.out.println("Certificate executed.");
        new File(filePath).delete();
    }

}
