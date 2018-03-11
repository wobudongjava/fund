package com.wo.bu.dong.common.ssh;

import org.junit.Test;

public class SFTPUtilsTest {

    @Test
    public void testUploadFile() {
        String pathLocalDir = "/local/";
        String pathRemoteDir = "sftpFile";
        SFTPUtils.uploadFile(pathLocalDir, pathRemoteDir);
    }

    @Test
    public void testDownloadFile() {
        String pathLocalDir = "/local/temp";
        String pathRemoteDir = "sftpFile";
        SFTPUtils.downloadFile(pathLocalDir, pathRemoteDir);
    }

}
