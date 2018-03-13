package com.wo.bu.dong.mock.supernet;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@XStreamAlias("ebank")

public class RemoteTransResp {
    private ResponseHead ResponseHead;

    @Getter
    @Setter
    public static class ResponseHead {
        private String Source;         //发起服务名"		M
        private String Destination;    //目标服务名"		M		商行IBPS系统为:IBPS
        private String TransactionId;  //交易码"		M
        private String SendDateTime;   //发送时间"		M
        private String ErrCode;        //错误码"		M	参见：错误码
        private String Message;        //错误信息"		M
        private String IBPSMsgId;      //报文标识号"		M		"用于唯一标识一笔交易
        private String SysAcceptDate;  //系统受理时间"		M		网银暂不关注
        private String DealSts;        //处理状态"		M		网银暂不关注
        private String DealDesc;       //处理信息"		M		网银暂不关注

        private String TransferFlowNo; //不需要的字段，文档未出现，实际返回值报文才有。 

    }

}
