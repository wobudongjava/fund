package com.wo.bu.dong.common.ssh;

import org.junit.Assert;
import org.junit.Test;

public class SSHUtilsTest {

    @Test
    public void testGetSession() {
        String host = "192.168.56.101";
        int port = 22;
        String userName = "root";
        String password = "123456";
        Assert.assertNotNull(SSHUtils.getSession(host, port, userName, password));
    }

}
