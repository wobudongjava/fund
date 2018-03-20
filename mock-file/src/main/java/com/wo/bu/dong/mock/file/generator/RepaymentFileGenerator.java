package com.wo.bu.dong.mock.file.generator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

//还款文件生成
public class RepaymentFileGenerator extends CSVFileGenerator {
    private String[] statistics;
    private String[] head = { "数据日期", "合同号", "贷款编号（借据号）", "还款流水号或理赔流水号", "还款类型", "管理机构号", "账务机构号", "还款期数", "还款日期", "流水号", "还款方式", "还款性质", "本金金额", "利息金额", "罚息金额", "其他金额", "还款账户类型",
            "还款银行代码", "还款账户名称", "还款账户号码", "还款账户开户支行", "还款账户开户所在省", "还款账户开户所在市", "源系统代码" };

    public RepaymentFileGenerator(File destFile, int lineNums, String dataDate) {
        super(destFile, lineNums, dataDate);
        statistics = new String[] { "当前文件总笔数：" + lineNums, "当前文件总金额：" + (lineNums * 910), "当天还款总笔数：" + (lineNums + 3), "当天还款总金额：" + ((lineNums + 3) * 910),
                "当天还款本金总金额：" + (lineNums * 900), "当天还款利息总金额：" + (lineNums * 10), "当天理赔总笔数：0", "当天理赔总金额：0" };
    }

    @Override
    protected String[] getStatistics() {
        return this.statistics;
    }

    @Override
    protected String[] getHead() {
        return this.head;
    }

    @Override
    protected List<String[]> getDataList(int lineNums) {
        String[] line = null;//行内容
        String dataDate = this.getDataDate();//数据日期
        String constractNo = null;//合同号
        String loanNo = null;//贷款编号
        String repayNo = null;//还款流水号
        String repayType = null;//还款类型
        String orderNo = null;//流水号
        String bjAmt = "900";//本金
        String lxAmt = "10";//利息
        String fxAmt = "0.0";//罚息
        String otherAmt = "0.0";//其它金额
        String rpDt = this.getDataDate();//还款日期

        List<String[]> dataList = new ArrayList<>();
        for (int i = 0; i < lineNums; i++) {
            //动态列
            constractNo = String.valueOf(System.nanoTime());
            loanNo = String.valueOf(System.nanoTime());
            repayNo = String.valueOf(System.nanoTime());
            //repayType = (i % 5 != 0) ? "1" : "2";
            repayType = "1";
            orderNo = String.valueOf(System.nanoTime());

            line = new String[] { dataDate, constractNo, loanNo, repayNo, repayType, "102", "102", "1", rpDt, orderNo, "1", "1", bjAmt, lxAmt, fxAmt, otherAmt, "200", "2345",
                    "谭某某", "2345678123456788", "上海晨晖支行", "上海", "上海", "202" };
            dataList.add(line);
        }
        return dataList;
    }

}
