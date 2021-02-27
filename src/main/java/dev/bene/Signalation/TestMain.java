package dev.bene.Signalation;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.whispersystems.signalservice.api.SignalServiceAccountManager;
import org.whispersystems.signalservice.api.push.TrustStore;

import java.io.*;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.cert.CertificateException;

public class TestMain {

    private static final String PIN        = "101010";
    private static final String USERNAME   = "+4915735986109";
    private static final String PASSWORD   = "whisper";
    private static final String USER_AGENT = "Signal iOS (+https://signal.org/download)";

    private static TrustStore trustStore;

    public static void main(String[] args) throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException {

        trustStore = new TrustStore() {
            @Override
            public InputStream getKeyStoreInputStream() {

                var f = new File("data/whisper.store");

                FileInputStream r = null;
                try {
                    r = new FileInputStream(f);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                return r;
            }

            @Override
            public String getKeyStorePassword() {
                return "whisper";
            }
        };

        Security.insertProviderAt(new SecurityProvider(), 1);
        Security.addProvider(new BouncyCastleProvider());

        var keyStore = KeyStore.getInstance("BKS");
        keyStore.load(trustStore.getKeyStoreInputStream(), trustStore.getKeyStorePassword().toCharArray());

        var config = ServiceConfiguration.getSignalServiceConfiguration(trustStore);
        var accountManager = new SignalServiceAccountManager(
                config,
                USERNAME,
                PASSWORD,
                USER_AGENT
        );
    }
}
