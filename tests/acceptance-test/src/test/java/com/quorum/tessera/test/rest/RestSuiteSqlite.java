package com.quorum.tessera.test.rest;

import com.quorum.tessera.config.CommunicationType;
import com.quorum.tessera.test.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    DeleteIT.class,
    MultipleKeyNodeIT.class,
    PushIT.class,
    ReceiveIT.class,
    ReceiveRawIT.class,
    ResendAllIT.class,
    ResendIndividualIT.class,
    SendIT.class,
    SendRawIT.class,
    P2PRestAppIT.class,
    TransactionForwardingIT.class,
    AdminConfigIT.class,
    CucumberRestIT.class,
    CucumberRawIT.class,
    CucumberAdminIT.class,
    CucumberWhitelistIT.class
})
public class RestSuiteSqlite {

    private static final ProcessManager PROCESS_MANAGER = new ProcessManager(CommunicationType.REST, DBType.SQLITE);

    @BeforeClass
    public static void onSetup() throws Exception {
        PROCESS_MANAGER.startNodes();
    }

    @AfterClass
    public static void onTearDown() throws Exception {
        PROCESS_MANAGER.stopNodes();

    }
    
    
}
