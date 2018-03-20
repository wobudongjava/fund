package com.wo.bu.dong.mock.file.generator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class IntCtFileGenerator extends CSVFileGenerator {
    private String[] statistics;
    private String[] head = { "数据日期", "贷款编号（借据号）", "业务发生日期", "放款日期", "到期日期", "还款期数", "本期起始日", "本期到期日", "贷款本金", "贷款剩余本金", "执行利率", "利率类型", "基准天数", "计提利息", "还款方式", "源系统代码" };

    public IntCtFileGenerator(File destFile, int lineNums, String dataDate) {
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
        String loanNo = null;//贷款编号
        String bjAmt = "1500";//贷款本金
        List<String[]> dataList = new ArrayList<>();
        for (int i = 0; i < lineNums; i++) {
            //动态列
            loanNo = String.valueOf(System.nanoTime());
            line = new String[] { dataDate, loanNo, dataDate, dataDate, "20170331", "3", "20170101", "20170131", bjAmt, "0", "0", "M", "360", "0", "1", "202" };
            dataList.add(line);
        }
        return dataList;
    }

}
