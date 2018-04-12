package com.wo.bu.dong.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import com.wo.bu.dong.common.exception.ZipException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ZipUtils {
    private ZipUtils() {
    }

    /**
     * 压缩源文件为zip文件
     * 
     * @param src 源文件（待压缩文件）
     * @param dest 目标文件（压缩后的文件）
     */
    public static void zip(File src, File dest) {
        //创建zip归档输出流
        try (BufferedOutputStream destOut = new BufferedOutputStream(FileUtils.openOutputStream(dest));
                ZipArchiveOutputStream zipOutput = (ZipArchiveOutputStream) new ArchiveStreamFactory().createArchiveOutputStream(ArchiveStreamFactory.ZIP, destOut);) {
            //压缩文件
            ZipArchiveEntry zipEntry = new ZipArchiveEntry(src, src.getName());
            zipOutput.putArchiveEntry(zipEntry);
            try (FileInputStream srcInput = FileUtils.openInputStream(src);) {
                IOUtils.copy(srcInput, zipOutput);
                zipOutput.closeArchiveEntry();
            }
        } catch (Exception e) {
            log.error("ZipUtils.zip, zip exception:{}", e.getMessage(), e);
            throw new ZipException(e.getMessage(), e);
        }
    }

    /**
     * 解压zip文件
     * 
     * @param src 源文件（待解压文件）
     * @param dest 目标文件（解压后的文件）
     */
    public static void unzip(File src, File dest) {
        //创建zip归档输入流
        try (BufferedInputStream in = new BufferedInputStream(FileUtils.openInputStream(src));
                ZipArchiveInputStream zipInput = (ZipArchiveInputStream) new ArchiveStreamFactory().createArchiveInputStream(ArchiveStreamFactory.ZIP, in);) {
            //解压文件
            ZipArchiveEntry zipEntry = null;
            while ((zipEntry = zipInput.getNextZipEntry()) != null) {
                if (zipEntry.isDirectory()) {
                    continue;
                }
                //解压到目标文件
                try (FileOutputStream destOut = FileUtils.openOutputStream(dest);) {
                    IOUtils.copy(zipInput, destOut);
                }
            }
        } catch (Exception e) {
            log.error("ZipUtils.unzip, unzip exception:{}", e.getMessage(), e);
            throw new ZipException(e.getMessage(), e);
        }
    }

}
