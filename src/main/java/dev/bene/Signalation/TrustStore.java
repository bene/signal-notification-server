package dev.bene.Signalation;

import java.io.*;

public class TrustStore implements org.whispersystems.signalservice.api.push.TrustStore {

    private final File file;
    private final String password;

    public TrustStore(File file, String password) {
        this.file = file;
        this.password = password;
    }

    @Override
    public InputStream getKeyStoreInputStream() {

        FileInputStream stream = null;
        try {
            stream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return stream;
    }

    @Override
    public String getKeyStorePassword() {
        return password;
    }
}
