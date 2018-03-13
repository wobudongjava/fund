package com.wo.bu.dong.mock.supernet;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@XStreamAlias("ebank")
public class RemoteTransQueryReq {
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
        private String MsgId; //交易流水号
    }

    /*
     * public static void main(String argv[]) {
     * RemoteTransQueryReq req = new RemoteTransQueryReq();
     * RemoteTransQueryReq.RequestFields fields = new RequestFields();
     * req.setRequestFields(fields);
     * RemoteTransQueryReq.RequestHead head = new RequestHead();
     * req.setRequestHead(head);
     * head.Source = "source";
     * head.Destination = "dest\r\ntest";
     * head.TransactionId = "trans";
     * head.SendDateTime = "sendTime";
     * fields.MsgId = "msgId";
     * System.out.println(XmlBean.toXml(req));
     * String str = XmlBean.toXml(req);
     * RemoteTransQueryReq reqResult = XmlBean.toBean(str,
     * RemoteTransQueryReq.class);
     * System.out.println(XmlBean.toXml(reqResult));
     * System.out.println(reqResult);
     * }
     */
}
