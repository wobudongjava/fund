package com.wo.bu.dong.common.lock;

public enum BusinessPrefixEnum {
    SAMPLE("样例"),
    TRADE("交易"),
    BATCH("定时任务");
    private String summary;

    BusinessPrefixEnum(String summary) {
        this.summary = summary;
    }

    public String getSummary() {
        return summary;
    }

}
