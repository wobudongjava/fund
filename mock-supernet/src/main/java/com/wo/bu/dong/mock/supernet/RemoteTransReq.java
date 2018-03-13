package com.wo.bu.dong.mock.supernet;

import java.math.BigDecimal;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@XStreamAlias("ebank")
public class RemoteTransReq {
    private RequestHead   RequestHead;
    private RequestFields RequestFields;

    @Getter
    @Setter
    public static class RequestHead {
        private String Source;        //        发起服务名
        private String Destination;   //目标服务名商行IBPS系统为:IBPS
        private String TransactionId; //交易码
        private String SendDateTime;  // 发送时间
    }

    @Getter
    @Setter
    public static class RequestFields {
        private String     MsgId;           //交易流水号
        private String     EndToEndId;      //端到端标识号	Max35Text	M	同交易流水号
        private String     PmtTp;           //业务类型编码	Max4Text	M	参见：业务类型
        private String     PmtKd;           //业务种类编码	Max5Text	M	参见：业务种类
        private String     Currency;        //币种	MaxMin3Text	M
        private BigDecimal Amount;          //交易金额"	DecimalNumber	M
        private BigDecimal Charge;          //手续费金额"	DecimalNumber	M
        private String     AccptncDtTm;     //委托时间	ISODateTime	M
        private String     DbtrAcctId;      //付款人账号"	Max32Text	M
        private String     DbtrNm;          //付款人户名"	Max60Text	M
        //private String     DbtrAcctTp;     //付款人账户类型"	AcctTypeCode	O
        private String     DbtrIssr;        //付款人开户行名称"	Max35Text	O
        private String     DbtrMmbId;       //付款清算行行号"	Max12NumericText	O
        private String     DbtrBrnchId;     //付款人开户行所属网银系统行号"	Max12NumericText	O
        private String     DbtrCtrySubDvsn; //付款人开户行所属城市代码"	Max4Text	O
        private String     CdtrAcctId;      //收款人账号"	Max32Text	M
        private String     CdtrNm;          //收款人户名"	Max60Text	M
        private String     CdtrMmbId;       //收款清算行行号"	Max12NumericText	M
        private String     CdtrBrnchId;     //收款人开户行所属网银系统行号"	Max12NumericText	M
        private String     RmkInf;          //附言"	Max140Text	O
    }

}
