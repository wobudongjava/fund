package com.wo.bu.dong.mock.file.generator;

import java.io.File;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import com.wo.bu.dong.mock.file.enums.FileNamePrefixEnum;
import com.wo.bu.dong.mock.file.util.RSAUtils;
import com.wo.bu.dong.mock.file.util.ZipUtils;

public class RSACSVZipFileUtils {

    //私钥
    private static String PRIVATEKEY = "";

    //客户信息
    public static void generateCustFile(int count, String fileDate, String fileNum) throws Exception {
        String dirName = FileNamePrefixEnum.LOAN_USER.getCode();
        File csvfile = new File("/ftpDir/" + dirName + "_" + fileDate + "_" + fileNum + ".csv");//源文件
        File rsaFile = new File("/ftpDir/" + dirName + "_" + fileDate + "_" + fileNum + ".csv.rsa");//私钥加密+base64文件
        File zipFile = new File("/ftpDir/" + dirName + "_" + fileDate + "_" + fileNum + ".csv.rsa.zip");//rsaFile的压缩文件
        int lineNums = count;//生成笔数
        //删除文件
        csvfile.delete();
        rsaFile.delete();
        zipFile.delete();
        //生成源文件
        new CustomerFileGenerator(csvfile, lineNums, fileDate).generateFile();
        //加密
        encryptFile(csvfile, rsaFile);
        //加密文件压缩
        compressFile(rsaFile, zipFile);
    }

    //借款
    public static void generateLnFile(int count, String fileDate, String fileNum) throws Exception {
        String dirName = FileNamePrefixEnum.LOAN.getCode();
        File csvfile = new File("/ftpDir/" + dirName + "_" + fileDate + "_" + fileNum + ".csv");//源文件
        File rsaFile = new File("/ftpDir/" + dirName + "_" + fileDate + "_" + fileNum + ".csv.rsa");//私钥加密+base64文件
        File zipFile = new File("/ftpDir/" + dirName + "_" + fileDate + "_" + fileNum + ".rsa.zip");//rsaFile的压缩文件
        int lineNums = count;//生成笔数
        //删除文件
        csvfile.delete();
        rsaFile.delete();
        zipFile.delete();
        //生成源文件
        new LoanFileGenerator(csvfile, lineNums, fileDate).generateFile();
        //加密
        encryptFile(csvfile, rsaFile);
        //加密文件压缩
        compressFile(rsaFile, zipFile);
    }

    //还款
    public static void generateRptmFile(int count, String fileDate, String fileNum) throws Exception {
        String dirName = FileNamePrefixEnum.REPAY.getCode();
        File csvfile = new File("/ftpDir/" + dirName + "_" + fileDate + "_" + fileNum + ".csv");//源文件
        File rsaFile = new File("/ftpDir/" + dirName + "_" + fileDate + "_" + fileNum + ".csv.rsa");//私钥加密+base64文件
        File zipFile = new File("/ftpDir/" + dirName + "_" + fileDate + "_" + fileNum + ".csv.rsa.zip");//rsaFile的压缩文件
        int lineNums = count;//生成笔数
        //删除文件
        csvfile.delete();
        rsaFile.delete();
        zipFile.delete();
        //生成源文件
        new RepaymentFileGenerator(csvfile, lineNums, fileDate).generateFile();
        //加密
        encryptFile(csvfile, rsaFile);
        //加密文件压缩
        compressFile(rsaFile, zipFile);
    }

    //还款计划
    public static void generateRptmSchdFile(int count, String fileDate, String fileNum) throws Exception {
        String dirName = FileNamePrefixEnum.REPAY_PLAN.getCode();
        File csvfile = new File("/ftpDir/" + dirName + "_" + fileDate + "_" + fileNum + ".csv");//源文件
        File rsaFile = new File("/ftpDir/" + dirName + "_" + fileDate + "_" + fileNum + ".csv.rsa");//私钥加密+base64文件
        File zipFile = new File("/ftpDir/" + dirName + "_" + fileDate + "_" + fileNum + ".csv.rsa.zip");//rsaFile的压缩文件
        int lineNums = count;//生成笔数
        //删除文件
        csvfile.delete();
        rsaFile.delete();
        zipFile.delete();
        //生成源文件
        new RepaymentScheduleFileGenerator(csvfile, lineNums, fileDate).generateFile();
        //加密
        encryptFile(csvfile, rsaFile);
        //加密文件压缩
        compressFile(rsaFile, zipFile);
    }

    //贷款状态
    public static void generateStsFile(int count, String fileDate, String fileNum) throws Exception {
        String dirName = FileNamePrefixEnum.LOAN_STATISTICS.getCode();
        File csvfile = new File("/ftpDir/" + dirName + "_" + fileDate + "_" + fileNum + ".csv");//源文件
        File rsaFile = new File("/ftpDir/" + dirName + "_" + fileDate + "_" + fileNum + ".csv.rsa");//私钥加密+base64文件
        File zipFile = new File("/ftpDir/" + dirName + "_" + fileDate + "_" + fileNum + ".csv.rsa.zip");//rsaFile的压缩文件
        int lineNums = count;//生成笔数
        //删除文件
        csvfile.delete();
        rsaFile.delete();
        zipFile.delete();
        //生成源文件
        new LoanStatusFileGenerator(csvfile, lineNums, fileDate).generateFile();
        //加密
        encryptFile(csvfile, rsaFile);
        //加密文件压缩
        compressFile(rsaFile, zipFile);
    }

    //日息计提
    public static void generateIntCtFile(int count, String fileDate, String fileNum) throws Exception {
        String dirName = FileNamePrefixEnum.INTEREST_INT_CT.getCode();
        File csvfile = new File("/ftpDir/" + dirName + "_" + fileDate + "_" + fileNum + ".csv");//源文件
        File rsaFile = new File("/ftpDir/" + dirName + "_" + fileDate + "_" + fileNum + ".csv.rsa");//私钥加密+base64文件
        File zipFile = new File("/ftpDir/" + dirName + "_" + fileDate + "_" + fileNum + ".csv.rsa.zip");//rsaFile的压缩文件
        int lineNums = count;//生成笔数
        //删除文件
        csvfile.delete();
        rsaFile.delete();
        zipFile.delete();
        //生成源文件
        new IntCtFileGenerator(csvfile, lineNums, fileDate).generateFile();
        //加密
        encryptFile(csvfile, rsaFile);
        //加密文件压缩
        compressFile(rsaFile, zipFile);
    }

    public static void encryptFile(File sourceFile, File destFile) throws Exception {
        System.out.println("encode->start" + DateFormatUtils.format(new Date(), "yyyyMMdd hh:mm:ss"));
        //私钥加密
        byte[] data = FileUtils.readFileToByteArray(sourceFile);
        byte[] encodedData = RSAUtils.encryptByPrivateKey(data, PRIVATEKEY);
        //编码 to base64
        String base64EncodedData = Base64.encodeBase64String(encodedData);
        //写加密文件
        FileUtils.writeByteArrayToFile(destFile, base64EncodedData.getBytes());
        System.out.println("encode->end" + DateFormatUtils.format(new Date(), "yyyyMMdd hh:mm:ss"));
    }

    public static void compressFile(File sourceFile, File destFile) {
        System.out.println("zip->start" + DateFormatUtils.format(new Date(), "yyyyMMdd hh:mm:ss"));
        //压缩加密文件
        ZipUtils.zip(sourceFile, destFile);
        System.out.println("zip->end" + DateFormatUtils.format(new Date(), "yyyyMMdd hh:mm:ss"));
    }

    public static void main(String[] args) throws Exception {
        generateCustFile(100, "20171126", "001");
    }

}
