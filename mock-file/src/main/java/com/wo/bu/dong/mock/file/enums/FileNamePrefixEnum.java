package com.wo.bu.dong.mock.file.enums;

public enum FileNamePrefixEnum {
    //customer
    LOAN_USER("cust", "客户"),
    //loan
    LOAN("ln", "借款"),
    //repayment
    REPAY("rptm", "还款"),
    //repaymentPlan
    REPAY_PLAN("rptm_schd", "还款计划"),
    //loanStats
    LOAN_STATISTICS("sts", "贷款状态"),
    //Interest provision
    INTEREST_INT_CT("int_ct", "日息计提"),
    //balance
    ACCOUNT_BALANCE_DIR("balance", "对账");

    private String code;
    private String messsage;

    private FileNamePrefixEnum(String code, String messsage) {
        this.code = code;
        this.messsage = messsage;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMesssage() {
        return messsage;
    }

    public void setMesssage(String messsage) {
        this.messsage = messsage;
    }

    @Override
    public String toString() {
        return this.messsage;
    }

}
