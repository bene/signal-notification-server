package dev.bene.Signalation;

import org.whispersystems.signalservice.api.push.TrustStore;
import org.whispersystems.signalservice.internal.configuration.SignalCdnUrl;
import org.whispersystems.signalservice.internal.configuration.SignalContactDiscoveryUrl;
import org.whispersystems.signalservice.internal.configuration.SignalServiceConfiguration;
import org.whispersystems.signalservice.internal.configuration.SignalServiceUrl;

public class ServiceConfiguration {

    public static SignalServiceConfiguration getSignalServiceConfiguration(TrustStore trustStore) {
        return new SignalServiceConfiguration(
                new SignalServiceUrl[]{
                        new SignalServiceUrl("https://textsecure-service.whispersystems.org", trustStore)
                },
                new SignalCdnUrl[]{
                        new SignalCdnUrl("https://cdn.signal.org", trustStore),
                        new SignalCdnUrl("https://cdn2.signal.org", trustStore)
                },
                new SignalContactDiscoveryUrl[]{
                        new SignalContactDiscoveryUrl("https://api.directory.signal.org", trustStore)
                }
        );
    }
}
