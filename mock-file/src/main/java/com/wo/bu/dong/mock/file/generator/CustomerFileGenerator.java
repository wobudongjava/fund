package com.wo.bu.dong.mock.file.generator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.RandomUtils;

//客户信息生成
public class CustomerFileGenerator extends CSVFileGenerator {

    private String[] statistics;
    private String[] heads = { "数据日 期", "客户号", "客户名称", "管理机构号", "机构号", "管理客户经理", "性别", "出生日期", "婚姻状况", "最高学历", "最高学位", "客户状态", "民族", "政治面貌", "国别名称", "农户标志", "境内境外标志", "客户电子支付权限",
            "证件类型", "证件号码", "国籍", "住宅电话", "手机号码", "职业", "单位名称", "单位所属行业", "单位地址", "单位地址邮政编码", "本单位工作起始年份", "职务", "职称", "年收入", "工资账号", "工资账户开户银行", "居住地址", "居住地址邮政编码", "居住状况",
            "单位电话", "电子邮箱", "通讯地址", "通讯地址邮政编码", "户籍地址", "配偶姓名", "配偶证件类型", "配偶证件号码", "配偶工作单位", "配偶联系电话", "源系统代码" };

    public CustomerFileGenerator(File destFile, int lineNums, String dataDate) {
        super(destFile, lineNums, dataDate);
        statistics = new String[] { "当前文件总笔数：" + lineNums };
    }

    @Override
    protected List<String[]> getDataList(int lineNums) {
        String[] line = null;//行内容
        String dataDate = this.getDataDate();//数据日期
        String custId = null;//客户号
        String custName = "cust_" + dataDate;//客户名称
        String idNo = null;//证件号
        String idNo2 = null;//配偶证件号
        String yearlyIncome = "500000";//年收入
        List<String[]> dataList = new ArrayList<>();
        for (int i = 0; i < lineNums; i++) {
            //动态列
            custId = String.valueOf(System.nanoTime());
            idNo = "23145819900101" + String.valueOf(RandomUtils.nextLong(1000, 10000));
            idNo2 = "23145819900101" + String.valueOf(RandomUtils.nextLong(1000, 10000));
            line = new String[] { dataDate, custId, custName, "1001", "1002", "aaa", "1", "1990606", "10", "20", "4", "A", "", "", "", "1", "Y", "10", "2", idNo, "CHN", "", "",
                    "1", "上海某科技公司", "J", "", "", "", "3", "0", yearlyIncome, "88888", "上海工商银行", "上海浦东新区", "200100", "5", "021-1234567", "1234567@163.com", "上海浦东", "200101", "上海浦东",
                    "谭某某", "0", idNo2, "上海咸鱼公司", "15505918888", "1001", };
            dataList.add(line);
        }
        return dataList;
    }

    @Override
    protected String[] getHead() {
        return this.heads;
    }

    @Override
    protected String[] getStatistics() {
        return this.statistics;
    }
}
