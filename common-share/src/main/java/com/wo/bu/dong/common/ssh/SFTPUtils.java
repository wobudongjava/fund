package com.wo.bu.dong.common.ssh;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

import org.apache.commons.io.IOUtils;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;
import com.wo.bu.dong.common.exception.SFTPException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SFTPUtils {

    private static String host     = "192.168.56.101";
    private static int    port;
    private static String userName = "root";
    private static String password = "123456";

    /**
     * 上传本地目录下的文件到远程目录
     * 
     * @param pathLocalDir 本地目录
     * @param pathRemoteDir 远程目录
     */
    public static void uploadFile(String pathLocalDir, String pathRemoteDir) {
        log.info("uploadFile==> begin, params={pathLocalDir:{},pathRemoteDir:{}}", pathLocalDir, pathRemoteDir);
        //建立会话
        Session session = SSHUtils.getSession(host, port, userName, password);
        if (null == session) {
            log.warn("uploadFile, session obtain fail");
            throw new SFTPException("session obtain fail");
        }
        ChannelSftp sftpChannel = null;
        try {
            //校验本地目录
            File localDir = new File(pathLocalDir);
            if (!(localDir.exists() && localDir.isDirectory())) {
                log.warn("uploadFile, local dir notExist,pathLocalDir:[{}]", localDir.getAbsolutePath());
                throw new SFTPException("local dir notExist");
            }
            //校验本地目录是否有文件
            File[] localListFiles = localDir.listFiles();
            if (null == localListFiles || 0 == localListFiles.length) {
                log.warn("uploadFile,  no file in the local dir,pathLocalDir:[{}]", localDir.getAbsolutePath());
            }
            //建立sftp通道
            sftpChannel = (ChannelSftp) session.openChannel("sftp");
            sftpChannel.connect();
            if (!sftpChannel.isConnected()) {
                log.warn("uploadFile, sftpChannel connection fail");
                throw new SFTPException("sftpChannel connection fail");
            }
            //校验远程目录，不存在则创建
            String[] splitRemoteFolders = pathRemoteDir.split("/");
            for (String folder : splitRemoteFolders) {
                if (folder.length() > 0) {
                    try {
                        sftpChannel.cd(folder);
                    } catch (SftpException e) {
                        sftpChannel.mkdir(folder);
                        sftpChannel.cd(folder);
                    }
                }
            }
            //回到用户目录
            sftpChannel.cd(sftpChannel.getHome());
            //开始上传本地文件
            String sourceFile = null;//本地文件
            String targetFile = null;//远程文件
            for (File file : localListFiles) {
                sourceFile = pathLocalDir + "/" + file.getName();
                targetFile = pathRemoteDir + "/" + file.getName();
                try (FileInputStream in = new FileInputStream(sourceFile); OutputStream out = sftpChannel.put(targetFile, ChannelSftp.OVERWRITE);) {
                    log.info("uploadFile, ready upload {}", sourceFile);
                    IOUtils.copy(in, out, 1024 * 512);
                    log.info("uploadFile, upload {} successful", sourceFile);
                }
            }
        } catch (Exception e) {
            log.error("uploadFile, upload exception:{}", e.getMessage(), e);
            throw new SFTPException("upload exception", e);
        } finally {
            if (sftpChannel != null) {
                sftpChannel.exit();
            }
            if (session != null) {
                session.disconnect();
            }
        }
        log.info("uploadFile==> end");
    }

    /**
     * 下载远程目录下的文件到本地目录
     * 
     * @param pathLocalDir 本地目录
     * @param pathRemoteDir 远程目录
     * @return
     */
    public static List<File> downloadFile(String pathLocalDir, String pathRemoteDir) {
        log.info("downloadFile==> begin, params={pathLocalDir:{},pathRemoteDir:{}}", pathLocalDir, pathRemoteDir);
        //建立会话
        Session session = SSHUtils.getSession(host, port, userName, password);
        if (null == session) {
            log.warn("downloadFile, session obtain fail");
            throw new SFTPException("session obtain fail");
        }
        ChannelSftp sftpChannel = null;
        try {
            //建立sftp通道
            sftpChannel = (ChannelSftp) session.openChannel("sftp");
            sftpChannel.connect();
            if (!sftpChannel.isConnected()) {
                log.warn("downloadFile, sftpChannel connection fail");
                throw new SFTPException("sftpChannel connection fail");
            }
            //下载文件到本地
            @SuppressWarnings("rawtypes")
            Vector lsListFiles = sftpChannel.ls(pathRemoteDir);
            if (lsListFiles.isEmpty()) {
                log.warn("downloadFile, no file in the remote dir");
                return null;
            }
            List<File> result = new ArrayList<>();
            LsEntry lsEntry = null;
            String fileName = null;
            SftpATTRS fileAttrs = null;
            File fileLocalDir = new File(pathLocalDir);
            if (!fileLocalDir.exists()) {
                fileLocalDir.mkdirs();
            }
            for (Object item : lsListFiles) {
                lsEntry = (LsEntry) item;
                fileName = lsEntry.getFilename();
                fileAttrs = lsEntry.getAttrs();
                if (fileAttrs.isDir()) {
                    continue;
                }
                //下载文件
                log.info("downloadFile, ready download {} ", fileName);
                sftpChannel.get(pathRemoteDir + "/" + fileName, fileLocalDir.getAbsolutePath());
                log.info("downloadFile, download {}  successful", fileName);
                //添加到result
                result.add(new File(pathLocalDir, fileName));
            }
            //对result按文件名字排序
            Collections.sort(result, new Comparator<File>() {
                @Override
                public int compare(File f1, File f2) {
                    return f1.getName().compareTo(f2.getName());
                }
            });
            log.info("downloadFile==> end");
            return result;
        } catch (Exception e) {
            log.error("downloadFile, download exception:{}", e.getMessage(), e);
            throw new SFTPException("download exception", e);
        } finally {
            if (sftpChannel != null) {
                sftpChannel.exit();
            }
            if (session != null) {
                session.disconnect();
            }
        }
    }

}
