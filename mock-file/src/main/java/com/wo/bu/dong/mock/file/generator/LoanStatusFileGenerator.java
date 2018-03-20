package com.wo.bu.dong.mock.file.generator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

//贷款总状态文件生成
public class LoanStatusFileGenerator extends CSVFileGenerator {
    private String[] statistics;
    private String[] head = { "数据日期", "合同号", "贷款编号（借据号）", "已还期数", "拖欠期数", "贷款本金", "贷款剩余本金", "应收正常本金", "实收本金", "应收利息", "实收利息", "累计欠本", "累计欠息", "应收罚息", "实收罚息", "贷款状态", "代偿本金",
            "代偿利息", "核销金额", "客户名称", "证件类型", "证件号码", "贷款类型", "源系统代码" };

    public LoanStatusFileGenerator(File destFile, int lineNums, String dataDate) {
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

        List<String[]> dataList = new ArrayList<>();
        for (int i = 0; i < lineNums; i++) {
            //动态列
            constractNo = String.valueOf(System.nanoTime());
            loanNo = String.valueOf(System.nanoTime());

            line = new String[] { dataDate, constractNo, loanNo, "0", "0", bjAmt, bjAmt, bjAmt, "0", "100", "0", "0", "0", "0", "0", "1", "0", "0", "", "谭某某", "0",
                    "012345678912345678", "01", "202" };
            dataList.add(line);
        }
        return dataList;
    }

}
