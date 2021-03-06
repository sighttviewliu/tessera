package com.quorum.tessera.enclave;

import com.quorum.tessera.config.AppType;
import com.quorum.tessera.config.CommunicationType;
import com.quorum.tessera.config.Config;
import com.quorum.tessera.config.InetServerSocket;
import com.quorum.tessera.config.KeyConfiguration;
import com.quorum.tessera.config.ServerConfig;
import com.quorum.tessera.config.keypairs.ConfigKeyPair;
import com.quorum.tessera.config.keypairs.DirectKeyPair;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.Test;


public class EnclaveFactoryTest {

    private EnclaveFactory enclaveFactory;

    @Before
    public void onSetUp() {
        this.enclaveFactory = EnclaveFactory.create();
    }

    @Test
    public void create() {
        assertThat(enclaveFactory).isNotNull();
    }
    @Test
    public void createRemote() {
        final Config config = new Config();

        ServerConfig serverConfig = new ServerConfig();
        serverConfig.setEnabled(true);
        serverConfig.setApp(AppType.ENCLAVE);
        serverConfig.setCommunicationType(CommunicationType.REST);
        serverConfig.setServerSocket(new InetServerSocket("http://bogus", 9898));

        config.setServerConfigs(Arrays.asList(serverConfig));

        Enclave result = enclaveFactory.create(config);

        assertThat(result).isInstanceOf(EnclaveClient.class);

    }

    @Test
    public void dontCreateRemoteWhenNoEnclaveServer() {

        Stream.of(AppType.values()).filter(t -> t != AppType.ENCLAVE).forEach(t -> {

            final Config config = new Config();

            ServerConfig serverConfig = new ServerConfig();
            serverConfig.setApp(t);
            serverConfig.setCommunicationType(CommunicationType.REST);
            serverConfig.setServerSocket(new InetServerSocket("http://bogus", 9898));

            config.setServerConfigs(Arrays.asList(serverConfig));

            KeyConfiguration keyConfiguration = new KeyConfiguration();
            ConfigKeyPair pair = new DirectKeyPair("/+UuD63zItL1EbjxkKUljMgG8Z1w0AJ8pNOR4iq2yQc=", "yAWAJjwPqUtNVlqGjSrBmr1/iIkghuOh1803Yzx9jLM=");
            keyConfiguration.setKeyData(Arrays.asList(pair));
            config.setKeys(keyConfiguration);

            config.setAlwaysSendTo(new ArrayList<>());

            Enclave result = enclaveFactory.create(config);

            assertThat(result)
                    .isInstanceOf(EnclaveImpl.class);

        });

    }

    @Test
    public void createLocal() {

        Config config = new Config();

        KeyConfiguration keyConfiguration = new KeyConfiguration();
        ConfigKeyPair pair = new DirectKeyPair("/+UuD63zItL1EbjxkKUljMgG8Z1w0AJ8pNOR4iq2yQc=", "yAWAJjwPqUtNVlqGjSrBmr1/iIkghuOh1803Yzx9jLM=");
        keyConfiguration.setKeyData(Arrays.asList(pair));
        config.setKeys(keyConfiguration);

        config.setAlwaysSendTo(new ArrayList<>());

        Enclave result = enclaveFactory.create(config);

        assertThat(result).isInstanceOf(EnclaveImpl.class);

    }
    
    @Test
    public void createLocalExplicitly() {

        Config config = new Config();

        KeyConfiguration keyConfiguration = new KeyConfiguration();
        ConfigKeyPair pair = new DirectKeyPair("/+UuD63zItL1EbjxkKUljMgG8Z1w0AJ8pNOR4iq2yQc=", "yAWAJjwPqUtNVlqGjSrBmr1/iIkghuOh1803Yzx9jLM=");
        keyConfiguration.setKeyData(Arrays.asList(pair));
        config.setKeys(keyConfiguration);

        config.setAlwaysSendTo(new ArrayList<>());

        Enclave result = enclaveFactory.createLocal(config);

        assertThat(result).isInstanceOf(EnclaveImpl.class);

    }
}
