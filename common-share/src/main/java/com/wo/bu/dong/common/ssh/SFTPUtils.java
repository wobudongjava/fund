package com.wo.bu.dong.common.ssh;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SFTPUtils {

    private static String host;
    private static int    port;
    private static String userName;
    private static String password;

    public static void uploadFile(String pathLocalDir, String pathRemoteDir) {
        log.info("uploadFile==> begin, params={pathRemoteDir:{},pathLocalDir:{}}", pathRemoteDir, pathLocalDir);
        Session session = SSHUtils.getSession(host, port, userName, password);
        if (null == session) {
            log.warn("uploadFile, connection obtain fail");
            return;
        }
        ChannelSftp sftpChannel = null;
        try {
            //建立sftp通道
            sftpChannel = (ChannelSftp) session.openChannel("sftp");
            sftpChannel.connect();
            if (!sftpChannel.isConnected()) {
                log.warn("uploadFile, sftpChannel connection fail");
                return;
            }
            //上传本地目录下的文件
            File localDir = new File(pathLocalDir);
            if (localDir.exists() && localDir.isDirectory()) {
                //校验本地目录是否有文件
                File[] localListFiles = localDir.listFiles();
                if (0 == localListFiles.length) {
                    log.warn("uploadFile, local dir file is empty");
                    return;
                }
                //进入远程目录以检测目录是否存在，不存在则创建
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
                    OutputStream out = sftpChannel.put(targetFile, ChannelSftp.OVERWRITE);
                    byte[] buffer = new byte[256];
                    if (out != null) {
                        FileInputStream in = new FileInputStream(sourceFile);
                        int totalNumber;
                        while ((totalNumber = in.read(buffer)) > -1) {
                            out.write(buffer, 0, totalNumber);
                            out.flush();
                        }
                        //关闭IO流
                        out.close();
                        in.close();
                    }
                }
            } else {
                log.warn("uploadFile, local dir notExist or is empty");
            }
        } catch (Exception e) {
            log.error("uploadFile, upload exception:{}", e.getMessage(), e);
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

    public static List<File> downloadFile(String pathRemoteDir, String pathLocalDir) {
        Session session = SSHUtils.getSession(host, port, userName, password);
        if (null == session) {
            log.warn("downloadFile, connection obtain fail");
            return null;
        }
        List<File> result = null;
        ChannelSftp sftpChannel = null;
        try {
            //建立sftp通道
            sftpChannel = (ChannelSftp) session.openChannel("sftp");
            sftpChannel.connect();
            if (!sftpChannel.isConnected()) {
                log.warn("downloadFile, sftpChannel connection fail");
                return null;
            }
            //下载文件到本地
            @SuppressWarnings("rawtypes")
            Vector lsListFiles = sftpChannel.ls(pathRemoteDir);
            if (lsListFiles.size() > 0) {
                result = new ArrayList<>();
                LsEntry lsEntry = null;
                String fileName = null;
                SftpATTRS fileAttrs = null;
                for (Object item : lsListFiles) {
                    lsEntry = (LsEntry) item;
                    fileName = lsEntry.getFilename();
                    fileAttrs = lsEntry.getAttrs();
                    if (fileAttrs.isDir()) {
                        continue;
                    }
                    //下载文件
                    sftpChannel.get(pathRemoteDir + "/" + fileName, pathLocalDir + "/" + fileName);
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
                return result;
            }
        } catch (Exception e) {
            log.error("downloadFile, download exception:{}", e.getMessage(), e);
        } finally {
            if (sftpChannel != null) {
                sftpChannel.exit();
            }
            if (session != null) {
                session.disconnect();
            }
        }
        return null;
    }

}
