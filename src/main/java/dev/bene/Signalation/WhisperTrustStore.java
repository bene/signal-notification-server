package dev.bene.Signalation;

import org.whispersystems.signalservice.api.push.TrustStore;

import java.io.*;

public class WhisperTrustStore implements TrustStore {

    private InputStream inputStream;

    public WhisperTrustStore(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public InputStream getKeyStoreInputStream() {
        return inputStream;
    }

    @Override
    public String getKeyStorePassword() {
        return "whisper";
    }
}
