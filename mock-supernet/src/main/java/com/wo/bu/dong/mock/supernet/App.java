package com.wo.bu.dong.mock.supernet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class App {
    public static final int    PORT            = 5188;
    public static final String SERVER_MOCK_TXT = "server-mock.txt";

    public App() {
        try (ServerSocket ss = new ServerSocket(PORT);) {

            InetAddress ia = InetAddress.getByName(null);

            log.info("Server@" + ia + " start!");

            while (true) {
                Socket s = ss.accept();// listen PORT;
                try {
                    new ServerOne(s);
                } catch (IOException e) {
                    s.close();
                }
            }

        } catch (Exception e) {
            log.error("服务异常", e);
        }

    }

}

@Slf4j
class ServerOne extends Thread {
    private Socket         s;
    private BufferedReader in;
    private PrintWriter    out;

    /*
     * public static Map<String,String> status;
     * static{
     * status = new HashMap<>();
     * status.put("PR03", "已轧差");
     * status.put("PR04", "已清算");
     * status.put("PR08", "已撤销");
     * status.put("PR09", "已拒绝");
     * status.put("PE0000", "正常处理");
     * }
     */

    public ServerOne(Socket s) throws IOException {
        this.s = s;
        in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(s.getOutputStream())), true);
        start();
    }

    @Override
    public void run() {
        try {
            String msg = "";
            String str = "";
            while (msg != null) {
                msg = in.readLine();
                log.info("==>received:" + msg);
                str = str + msg;
            }
            log.info("==>recive str:" + str);
            str = str.substring(16);
            if (str.indexOf("<TransactionId>1000</TransactionId>") > 0) {
                RemoteTransReq reqDto = XmlBean.toBean(str, RemoteTransReq.class);
                log.info(reqDto.toString());
                RemoteTransResp respDto = new RemoteTransResp();

                int sleepTime = 1000;
                Map<String, String> config = getConfig();
                String dealSts = config.get("T_DealSts");
                String errorCode = config.get("T_ErrorCode");
                if (config.get("T_Sleep") != null && !"".equals(config.get("T_Sleep")))
                    sleepTime = Integer.parseInt(config.get("T_Sleep"));

                RemoteTransResp.ResponseHead head = new RemoteTransResp.ResponseHead();
                respDto.setResponseHead(head);

                head.setDealSts(dealSts);
                head.setSysAcceptDate(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));

                head.setErrCode(errorCode);
                String result = XmlBean.toXmlWithOutFormat(respDto);
                result = "<?xml version=\"1.0\" encoding=\"GB2312\"?>" + result;
                log.info("==>result=" + result);

                result = "1234567812345678" + result;

                Thread.sleep(sleepTime);
                out.println(result);
            } else if (str.indexOf("<TransactionId>1009</TransactionId>") > 0) {

                RemoteTransQueryReq reqDto = XmlBean.toBean(str, RemoteTransQueryReq.class);
                log.info(reqDto.toString());
                RemoteTransQueryResp respDto = new RemoteTransQueryResp();

                int sleepTime = 1000;
                Map<String, String> config = getConfig();
                String dealSts = config.get("Q_DealSts");
                String errorCode = config.get("Q_ErrorCode");
                if (config.get("Q_Sleep") != null && !"".equals(config.get("Q_Sleep")))
                    sleepTime = Integer.parseInt(config.get("Q_Sleep"));

                RemoteTransQueryResp.ResponseHead head = new RemoteTransQueryResp.ResponseHead();
                respDto.setResponseHead(head);

                head.setDealSts(dealSts);
                head.setSysAcceptDate(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));

                head.setErrCode(errorCode);
                String result = XmlBean.toXmlWithOutFormat(respDto);
                result = "<?xml version=\"1.0\" encoding=\"GB2312\"?>" + result;
                log.info("==>result=" + result);
                Thread.sleep(sleepTime);

                result = "1234567812345678" + result;
                out.println(result);
            }
        } catch (Exception e) {
            log.error("error", e);
        } finally {
            try {
                s.close();
            } catch (IOException e) {
            }
        }
    }

    public static void main(String[] args) {
        new App();
    }

    public static Map<String, String> getConfig() {

        Map<String, String> config = new HashMap<>();

        File file = new File(App.SERVER_MOCK_TXT);
        try {
            List<String> contentList = FileUtils.readLines(file, Charset.forName("GBK"));

            config.put("T_DealSts", contentList.get(0));
            config.put("T_ErrorCode", contentList.get(1));
            config.put("T_Sleep", contentList.get(2));
            config.put("Q_DealSts", contentList.get(4));
            config.put("Q_ErrorCode", contentList.get(5));
            config.put("Q_Sleep", contentList.get(6));

        } catch (IOException e) {
            log.error("read file exception.", e);
            e.printStackTrace();
        }
        return config;
    }
}
