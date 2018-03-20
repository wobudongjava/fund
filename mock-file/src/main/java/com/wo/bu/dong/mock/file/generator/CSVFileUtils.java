package com.wo.bu.dong.mock.file.generator;

import java.io.File;

public class CSVFileUtils {

    //生成文件
    public static void generateFile(CSVFileGenerator generator) {
        generator.generateFile();
    }

    //demo
    public static void main(String[] args) {
        //客户信息
        generateFile(new CustomerFileGenerator(new File("/ftpDir/customer.csv"), 100, "20171126"));
        //借款
        //generateFile(new LoanFileGenerator(new File("/ftpDir/loan.csv"), 100));
        //还款
        //generateFile(new RepaymentFileGenerator(new File("/ftpDir/repayment.csv"), 100));
        //还款计划
        //generateFile(new RepaymentScheduleFileGenerator(new File("/ftpDir/repaymentSchedule.csv"), 100));
        //贷款状态
        //generateFile(new LoanStatusFileGenerator(new File("/ftpDir/loanStatus.csv"), 100));
    }
}
