package com.wo.bu.dong.common.ssh;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SSHUtils {

    /**
     * @param host 主机
     * @param port 端口，如果port<=0，则设为22端口
     * @param userName 用户名
     * @param password 密码
     * @return null:会话建立失败或异常 notNull:会话建立成功
     */
    public static Session getSession(String host, int port, String userName, String password) {
        if (port <= 0) {
            port = 22;
        }
        JSch jsch = new JSch();
        Session session = null;
        try {
            session = jsch.getSession(userName, host, port);
            session.setConfig("PreferredAuthentications", "password");//首选认证方式-密码
            session.setConfig("StrictHostKeyChecking", "no");//由于使用密码认证，所以关闭gssapi认证（认证比较耗时）
            session.setPassword(password);
            session.connect(30000);
            session.setTimeout(60000);
            if (session.isConnected()) {
                log.info("SSH.getSession, Connection successful");
            } else {
                log.warn("SSH.getSession, Connection fail");
                return null;
            }
        } catch (JSchException e) {
            log.error("SSH.getSession, session connection exception:{}", e.getMessage(), e);
            return null;
        }
        return session;
    }

}
