package com.wo.bu.dong.mock.file.generator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

//还款计划文件生成
public class RepaymentScheduleFileGenerator extends CSVFileGenerator {
    private String[] statistics;
    private String[] head = { "数据日期", "合同号", "贷款编号（借据号）", "管理机构号", "账务机构号", "利率类型", "执行利率", "还款期数", "本金", "归还本金", "利息到期日", "计提利息", "利息", "归还利息", "累计罚息", "已还罚息", "应计利息", "利息收入",
            "逾期代码", "贷款原始本金", "贷款剩余本金", "还款计划状态", "用户还款日期", "减免利息", "源系统代码" };

    public RepaymentScheduleFileGenerator(File destFile, int lineNums, String dataDate) {
        super(destFile, lineNums, dataDate);
        statistics = new String[] { "当前文件总笔数：" + lineNums };
    }

    @Override
    protected String[] getStatistics() {
        return statistics;
    }

    @Override
    protected String[] getHead() {
        return head;
    }

    @Override
    protected List<String[]> getDataList(int lineNums) {
        String[] line = null;//行内容
        String dataDate = this.getDataDate();//数据日期
        String constractNo = null;//合同号
        String loanNo = null;//贷款编号
        String bjAmt = "1000";//本金
        String lxAmt = "0.5";//利息

        List<String[]> dataList = new ArrayList<>();
        for (int i = 0; i < lineNums; i++) {
            //动态列
            constractNo = String.valueOf(System.nanoTime());
            loanNo = String.valueOf(System.nanoTime());

            line = new String[] { dataDate, constractNo, loanNo, "102", "102", "M", "0.025", "1", bjAmt, "0", "20170707", "0", lxAmt, "0", "0", "0", "0", "100", "001", "6000",
                    "6000", i % 2 == 0 ? "0" : "1", "20170707", "1.5", "202" };
            dataList.add(line);
        }
        return dataList;
    }

}
