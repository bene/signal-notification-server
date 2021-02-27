package dev.bene.Signalation;

import org.whispersystems.libsignal.IdentityKey;
import org.whispersystems.libsignal.IdentityKeyPair;
import org.whispersystems.libsignal.InvalidKeyIdException;
import org.whispersystems.libsignal.SignalProtocolAddress;
import org.whispersystems.libsignal.state.PreKeyRecord;
import org.whispersystems.libsignal.state.SessionRecord;
import org.whispersystems.libsignal.state.SignalProtocolStore;
import org.whispersystems.libsignal.state.SignedPreKeyRecord;

import java.util.List;

public class JsonSignalProtocolStore implements SignalProtocolStore {
    @Override
    public IdentityKeyPair getIdentityKeyPair() {
        return null;
    }

    @Override
    public int getLocalRegistrationId() {
        return 0;
    }

    @Override
    public boolean saveIdentity(SignalProtocolAddress signalProtocolAddress, IdentityKey identityKey) {
        return false;
    }

    @Override
    public boolean isTrustedIdentity(SignalProtocolAddress signalProtocolAddress, IdentityKey identityKey, Direction direction) {
        return false;
    }

    @Override
    public IdentityKey getIdentity(SignalProtocolAddress signalProtocolAddress) {
        return null;
    }

    @Override
    public PreKeyRecord loadPreKey(int i) throws InvalidKeyIdException {
        return null;
    }

    @Override
    public void storePreKey(int i, PreKeyRecord preKeyRecord) {

    }

    @Override
    public boolean containsPreKey(int i) {
        return false;
    }

    @Override
    public void removePreKey(int i) {

    }

    @Override
    public SessionRecord loadSession(SignalProtocolAddress signalProtocolAddress) {
        return null;
    }

    @Override
    public List<Integer> getSubDeviceSessions(String s) {
        return null;
    }

    @Override
    public void storeSession(SignalProtocolAddress signalProtocolAddress, SessionRecord sessionRecord) {

    }

    @Override
    public boolean containsSession(SignalProtocolAddress signalProtocolAddress) {
        return false;
    }

    @Override
    public void deleteSession(SignalProtocolAddress signalProtocolAddress) {

    }

    @Override
    public void deleteAllSessions(String s) {

    }

    @Override
    public SignedPreKeyRecord loadSignedPreKey(int i) throws InvalidKeyIdException {
        return null;
    }

    @Override
    public List<SignedPreKeyRecord> loadSignedPreKeys() {
        return null;
    }

    @Override
    public void storeSignedPreKey(int i, SignedPreKeyRecord signedPreKeyRecord) {

    }

    @Override
    public boolean containsSignedPreKey(int i) {
        return false;
    }

    @Override
    public void removeSignedPreKey(int i) {

    }
}
