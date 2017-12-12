1.	download certificate for https://abc.com from mozilla firfox. and save with the name install-abc-cert

and execute below command to add it in java home.

2.	keytool -delete -alias install-abc-cert -storepass changeit -keystore "%JAVA_HOME%/jre/lib/security/cacerts"

3.	keytool -importcert -noprompt -trustcacerts -alias install-abc-cert -storepass changeit -file <fileParentDirPath>\downloadedCertificate.crt -keystore "%JAVA_HOME%/jre/lib/security/cacerts"

4.	Give permission to JAVA_HOME folder to the current user to execute keytool command without administrator permission.

5. 	Execute CertificateDownloader.java file to download and execute certificate installer.  