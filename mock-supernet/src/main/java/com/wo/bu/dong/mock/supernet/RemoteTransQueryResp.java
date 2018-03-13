package com.wo.bu.dong.mock.supernet;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@XStreamAlias("ebank")

public class RemoteTransQueryResp {
    private ResponseHead   ResponseHead;
    private ResponseFields ResponseFields;

    @Getter
    @Setter
    public static class ResponseHead {

        private String Source;        //发起服务名"		M
        private String Destination;   //目标服务名"		M		商行IBPS系统为:IBPS
        private String TransactionId; //交易码"		M

        private String SendDateTime;  //发送时间"		M
        private String ErrCode;       //错误码"		M	参见：错误码
        private String Message;       //错误信息"		M
        private String IBPSMsgId;     //报文标识号"		M		"用于唯一标识一笔交易
        private String SysAcceptDate; //系统受理时间"		M		网银暂不关注
        private String DealSts;       //处理状态"		M		网银暂不关注 //todo
        private String DealDesc;      //处理信息"		M		网银暂不关注
    }

    @Getter
    @Setter
    public static class ResponseFields {
        private String IBPSMsgId; //报文标识号
        private String ProcSts;   //业务状态
        private String ProcCd;    //         业务处理码
        private String PtyId;     //          拒绝业务的参与机构行号
        private String PtyProcCd; //                参与机构业务拒绝码
        private String RjctInf;   //业务拒绝信息
        private String TxNetgDt;  //                业务轧差日期或终态日期
        private String TxNetgRnd; //业务轧差场次
        private String TxSttlmDt; //业务清算日期
        private String DbtrNM;    //付款人名称
        private String DbtrAcct;  //付款人账号
        private String DbtrIssr;  //付款人开户行名称
        private String DbtrTp;    //         付款人账户类型
        private String RmkInf;    //备注
    }

}
