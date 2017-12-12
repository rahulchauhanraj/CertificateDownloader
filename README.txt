download certificate for https://abc.com from mozilla firfox. and save with the name install-abc-cert

and execute below command to add it in java home.

keytool -delete -alias install-abc-cert -storepass changeit -keystore "%JAVA_HOME%/jre/lib/security/cacerts"

keytool -importcert -noprompt -trustcacerts -alias install-abc-cert -storepass changeit -file <fileParentDirPath>\downloadedCertificate.crt -keystore "%JAVA_HOME%/jre/lib/security/cacerts"