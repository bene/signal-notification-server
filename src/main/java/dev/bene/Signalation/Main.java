package dev.bene.Signalation;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.whispersystems.libsignal.IdentityKeyPair;
import org.whispersystems.libsignal.InvalidKeyException;
import org.whispersystems.libsignal.state.PreKeyRecord;
import org.whispersystems.libsignal.util.KeyHelper;
import org.whispersystems.libsignal.util.guava.Optional;
import org.whispersystems.signalservice.api.SignalServiceAccountManager;
import org.whispersystems.signalservice.api.SignalServiceMessageSender;

import java.io.*;
import java.nio.file.Files;
import java.security.KeyStore;
import java.security.Security;
import java.util.List;

public class Main {

    private static List<PreKeyRecord> oneTimePreKeys = KeyHelper.generatePreKeys(0, 100);

    private static final String PIN        = "101010";
    private static final String USERNAME   = "+4915735986109";
    private static final String PASSWORD   = "GENERATE";
    private static final String USER_AGENT = "Signal iOS (+https://signal.org/download)";

    private static IdentityKeyPair IDENTITY_KEY;
    private static TrustStore WHISPER_TRUST_STORE;
    private static TrustStore IAS_TRUST_STORE;
    private static byte[] SENDER_KEY;

    private static SignalServiceAccountManager accountManager;

    public static void main(String[] args) throws Exception {

        setupSecurity();
        loadData();
        loadTrustStore();
        SENDER_KEY = loadSenderKey();

        var keyStore = KeyStore.getInstance("BKS");
        keyStore.load(WHISPER_TRUST_STORE.getKeyStoreInputStream(), WHISPER_TRUST_STORE.getKeyStorePassword().toCharArray());

        var config = ServiceConfiguration.getSignalServiceConfiguration(WHISPER_TRUST_STORE);



        accountManager = new SignalServiceAccountManager(
                config,
                USERNAME,
                PASSWORD,
                USER_AGENT
        );

        try {
            accountManager.verifyAccountWithCode("130683", PASSWORD, KeyHelper.generateRegistrationId(true), false, PIN, SENDER_KEY, true);
        }  catch (Exception e) {
            e.printStackTrace();
        }
        /*
        var captchaToken = "03AGdBq26mcZlIxQaGOcL9CUJa8H95CnkAW_BXtzDUJXJDBLPfhMLi4FH_ZKxCYSHKfBrznEi1kODI0n7UXlyadcweBIVOSHHyEBIZzgiaKjKD4Qw7bzmQqf8diQn2bV6NWJpTcxzMks3ZV1KypKHOOY0eaeVPZf0AB1U7ytkH4uPQIEhPnE-UzHyumZb1skmQA09yq_zbs9yiUHDS-6UNFstQjlm7p-uOKING2CDReHU_2K1m2rwFgFPSukQKln1G7Ui7Reb0mt8Cg3Wu8kLK2zFTyzOeGYp6udFWq4VtifBZJ6zxrIn9lL4sbwlt1TNzR0CbDIyZMFPlaiTtp6OOrz7zyBZgvawO3uC8wU3NkBzilQA3dRxPC2BkrHoBOTiqGJvz5Z82pe-3E3Mg2VoutmpUiy_Zk2OQatmdrg9HxbUDX6QolIigfS839-GZtvVhOtifsVJ5QuDn";
        try {
            requestSmsVerificationCode(Optional.fromNullable(captchaToken));
            System.out.println("Code sent!");
        } catch (IOException e) {
            System.out.println("CAPTCHA code required.");
        }*/

        /*

        accountManager.verifyAccountWithCode("874253", "", 1, false, "12345678", null, true);
        accountManager.setProfileName(TRUST_STORE.getKeyStoreInputStream().readAllBytes(), "Signalidy");

        SignalServiceMessageSender messageSender = new SignalServiceMessageSender("https://textsecure-service.whispersystems.org", TRUST_STORE, USERNAME, TRUST_STORE.getKeyStorePassword(),
                new MySignalProtocolStore(),
                USER_AGENT, Optional.absent());

        messageSender.sendMessage(new SignalServiceAddress("+14159998888"),
                SignalServiceDataMessage.newBuilder()
                        .withBody("Hello, world!")
                        .build());


        try {
            accountManager.requestSmsVerificationCode(false, Optional.fromNullable("03AGdBq24xBbfpZMWsEztdqYzeN8kTH1xdlpbZtcQuM8t9zzSAxFgTMoMaBBJAKsYQTlT3oJiwAbJ43awAz54vV71JepKEj7ALwQOC0FiK-5IhQiWDRwLAKtqpMARgpfx8mW_O3jJSpYxSPrTipQ1zb89EbRlejRz4kuOCSQNp3lqCPDDV6ysSmdFzslrRg6tB1e_xmqwT51QDHJWjEa0ZxDQ9XWXVvKLiZ6cGdx8w7ODh4lfRUq9ntluuiUKl2tx_wmW5urxc2nUz-09t3L1zEufZwsTksyNr3m8Schnijb5g7DMRipxpWFPnfJ4zi-uTuf4G1diZ2VCwMJHH_o6P3btGym0pelGnwVVb7G03jQpttR-GC0LcE_bk-lDXVdtRLsBfDkR6qZRAguw9MeRaECrABHohRsggszBkBnwJveYdbQT5xwfowbBkGaMvms1ta6YYv1WW7xcL"), Optional.absent());
            System.out.println("Code sent.");
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    public static void requestSmsVerificationCode(Optional<String> captchaToken) throws IOException {
        accountManager.requestSmsVerificationCode(false, captchaToken, Optional.absent());
    }

    public static void verifyAccountWithCode(String code) throws IOException {
        accountManager.verifyAccountWithCode(code, PASSWORD, 0, false, PIN, new byte[]{}, true);
    }

    public void sendMessage(String to, String message) {

    }

    public static void setupSecurity() {
        Security.insertProviderAt(new SecurityProvider(), 1);
        Security.addProvider(new BouncyCastleProvider());
    }

    public static byte[] loadSenderKey() throws IOException {

        var keyFile = new File("data/sender_key.txt");
        if (keyFile.exists()) {
            System.out.println("Loaded SenderKey");
            return new FileInputStream(keyFile).readAllBytes();
        } else {
            var key = KeyHelper.generateSenderKey();
            keyFile.createNewFile();
            Files.write(keyFile.toPath(), key);
            System.out.println("Crated SenderKey");
            return key;
        }
    }

    public static void loadTrustStore() throws FileNotFoundException {

        var whisperFile = new File("data/whisper.store");
        WHISPER_TRUST_STORE = new TrustStore(whisperFile, "whisper");
        System.out.println("Loaded WHISPER_TRUST_STORE");

        var iasFile = new File("data/ias.store");
        IAS_TRUST_STORE = new TrustStore(iasFile, "whisper");
        System.out.println("Loaded IAS_TRUST_STORE");
    }

    public static void loadData() throws IOException, InvalidKeyException {

        File keyPairFile = new File("data/IdentityKeyPair.bin");
        File dataDir = keyPairFile.getParentFile();

        if (dataDir.exists()) {
            if (!dataDir.isDirectory()) {
                throw new IOException("'data' must be dir not file.");
            }
        } else {
            Files.createDirectories(dataDir.toPath());
            loadData();
            return;
        }

        if (keyPairFile.exists()) {
            IDENTITY_KEY = new IdentityKeyPair(Files.readAllBytes(keyPairFile.toPath()));
            System.out.println("Loaded IdentityKeyPair");
        } else {
            IDENTITY_KEY = KeyHelper.generateIdentityKeyPair();
            Files.write(keyPairFile.toPath(), IDENTITY_KEY.serialize());
            System.out.println("Generated IdentityKeyPair");
        }
    }
}
