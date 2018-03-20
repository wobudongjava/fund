package com.wo.bu.dong.mock.file.generator;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

//借款文件生成
public class LoanFileGenerator extends CSVFileGenerator {
    private String[] statistics;
    private String[] head = { "数据日期", "合同号", "贷款编号（借据号）", "管理机构号", "账务机构号", "主办客户经理号", "协办客户经理号", "客户号", "借款交易流水号", "业务发生日期", "放款日期", "到期日期", "贷款期限", "放款金额", "币种", "放款账户类型",
            "放款银行代码", "放款账户名称", "放款账户号码", "放款账户开户支行", "放款账户开户所在省", "放款账户开户所在市", "本金还款频率", "利息还款频率", "还款方式", "利率期限单位", "利率类型", "基准利率", "利息上浮比例", "罚息利率", "执行利率", "提前还款违约金比率", "贷款余额",
            "担保方式", "产品代码", "账户类别", "贷款状态", "贷款类型", "扶贫贴息贷款标志", "农业综合开发贷款标志", "涉农贷款标志", "本金科目号", "利息科目号", "罚息科目号", "自主支付金额", "受托支付金额", "五级分类代码", "贷款形态", "贷款投向", "重组标志", "展期标志",
            "逾期标志", "逾期天数", "逾期宽限天数", "本金逾期日期", "利息逾期日期", "下一付息日", "下一利率重定价日", "逾期贷款余额", "首次逾期日", "逾期利率", "累计逾期利息", "表外欠息", "计息标志", "应计利息", "保证金金额", "专项准备", "特种准备", "抵减存款准备金方式",
            "授信号码", "代偿标志", "保单号", "保险金额", "保险费", "费用一", "费用二", "费用三", "产品ID", "利息计算方式", "客户名称", "证件类型", "证件号码", "源系统代码" };

    public LoanFileGenerator(File destFile, int lineNums, String dataDate) {
        super(destFile, lineNums, dataDate);
        statistics = new String[] { "当前文件总笔数：" + lineNums, "当前文件总金额：" + (lineNums * 1050), "当天总笔数：" + (lineNums + 3), "当天总金额：" + (lineNums + 3) * 1050 };
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
        //随机生成贷款期限
        int[][] lnTenorCalcData = { { 6, 1 }, { 6, 7 }, { 24, 13 }, { 24, 37 }, { 180, 61 } };
        Random rm_lnTenor = new Random();
        String[] line = null;//行内容
        String dataDate = this.getDataDate();//数据日期
        String custId = null;//客户号
        String transferNo = null;//借款交易流水号
        String constractNo = null;//合同号
        String loanNo = null;//贷款编号
        String fkAmt = null;//放款金额
        String baoDanNo = null;//保单号
        String lnTenor;//贷款期限 D 天 M 月
        String maturityDate = null;//到期日期
        String bsDate = this.getDataDate();//业务发生日期（可能和放款日期不一致）
        List<String[]> dataList = new ArrayList<>();
        for (int i = 0; i < lineNums; i++) {
            //动态列
            custId = String.valueOf(System.nanoTime());
            transferNo = String.valueOf(System.nanoTime());//借款交易流水号 = 放款流水号
            constractNo = String.valueOf(System.nanoTime());//合同号
            loanNo = String.valueOf(System.nanoTime());//贷款编号
            fkAmt = "1050";
            baoDanNo = String.valueOf(System.nanoTime());
            //随机生成贷款期限
            int index = rm_lnTenor.nextInt(6);
            if (index == 5) {
                //按天D
                lnTenor = "40D";
            } else {
                //按月M
                lnTenor = (rm_lnTenor.nextInt(lnTenorCalcData[index][0]) + lnTenorCalcData[index][1]) + "M";
            }
            //计算到期日期
            try {
                if (StringUtils.endsWith(lnTenor, "D")) {
                    maturityDate = DateFormatUtils
                            .format(DateUtils.addDays(DateUtils.parseDate(dataDate, "yyyyMMdd"), Integer.valueOf(lnTenor.substring(0, lnTenor.lastIndexOf('D'))) - 1), "yyyyMMdd");
                } else if (StringUtils.endsWith(lnTenor, "M")) {
                    maturityDate = DateFormatUtils.format(
                            DateUtils.addMonths(DateUtils.parseDate(dataDate, "yyyyMMdd"), Integer.valueOf(lnTenor.substring(0, lnTenor.lastIndexOf('M'))) - 1), "yyyyMMdd");
                }
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
            line = new String[] { dataDate, constractNo, loanNo, "1001", "1002", "1003", "1004", custId, transferNo, bsDate, dataDate, maturityDate, lnTenor, fkAmt, "RMB", "11",
                    "2001", "谭某某", "2341567891018421", "上海晨晖支行", "上海", "上海", "3", "3", "03", "2", "F", "0.025", "0", "0.001", "0.025", "0.001", fkAmt, "C", "3001", "2", "1", "01",
                    "N", "N", "N", "bj001", "lx001", "fx001", fkAmt, "", "1", "10", "", "N", "N", "N", "0", "30", "", "", "", "", "", "", "0.001", "", "", "", "", "1000", "1000",
                    "500", "", "credit001", "", baoDanNo, "1000", "500", "1", "2", "3", "1", "1", "谭某某", "0", "012345678912345678", "202" };
            dataList.add(line);
        }
        return dataList;
    }

}
