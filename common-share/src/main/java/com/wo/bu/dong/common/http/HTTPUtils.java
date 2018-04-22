package com.wo.bu.dong.common.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.wo.bu.dong.common.exception.HTTPRequestException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HTTPUtils {

    private static CloseableHttpClient httpClient;                                                               //请求客户端（可视为浏览器）

    public static final String         CHARSET_UTF_8                = Consts.UTF_8.name();                       //默认UTF-8编码

    public static final String         CONTENTTYPE_APPLICATION_JSON = ContentType.APPLICATION_JSON.getMimeType();//内容类型application/json

    private HTTPUtils() {
    }

    /**
     * HTTP post请求
     * 
     * @param req
     * @return
     * @throws HTTPRequestException
     */
    public static String post(final HttpReq req) throws HTTPRequestException {
        try {
            return postWithException(req);
        } catch (Exception e) {
            log.error("post, error msg:{}", e.getMessage());
            throw new HTTPRequestException(e);
        }
    }

    /**
     * HTTP get 请求
     * 
     * @param req
     * @return
     * @throws HTTPRequestException
     */
    public static String get(final HttpReq req) throws HTTPRequestException {
        try {
            return getWithException(req);
        } catch (Exception e) {
            log.error("get, error msg:{}", e.getMessage());
            throw new HTTPRequestException(e);
        }
    }

    /**
     * HTTP post请求
     * 
     * @param req
     * @return
     * @throws Exception
     */
    private static String postWithException(final HttpReq req) throws Exception {
        log.info("post==> begin, params={}", req);
        //step1:校验参数
        if (!checkHttpReq(req)) {
            return null;
        }
        //step2:获取客户端
        CloseableHttpClient httpClient = getHttpClient();
        //step3:构建请求
        HttpPost httpPost = new HttpPost(req.getUrl());
        //配置请求
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(1000)//连接超时时间
                .setSocketTimeout(10 * 1000)//读取超时时间
                .build();
        httpPost.setConfig(requestConfig);
        //设置请求参数
        Map<String, Object> params = req.getParams();
        String contentType = req.getContentType();
        String paramCharset = StringUtils.isBlank(req.getParamCharset()) ? CHARSET_UTF_8 : req.getParamCharset();
        if (MapUtils.isNotEmpty(params)) {
            if (StringUtils.isEmpty(req.getContentType()) || ContentType.APPLICATION_FORM_URLENCODED.getMimeType().equalsIgnoreCase(contentType)) {
                //form表单格式
                httpPost.setEntity(convertMapParamToUrlEncodedFormEntity(params, paramCharset));
            } else if (ContentType.APPLICATION_JSON.getMimeType().equalsIgnoreCase(contentType)) {
                //json格式
                httpPost.setEntity(new StringEntity(new Gson().toJson(params), ContentType.APPLICATION_JSON.withCharset(paramCharset)));
            }
        }
        //step4:执行请求
        log.info("post, Executing request {}", httpPost.getRequestLine());
        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
        log.info("post, httpResponse:{}", httpResponse);
        //step5:处理响应
        String result = responseHandler(httpResponse);
        log.info("post, responseBody:{}", result);
        log.info("post==> end");
        return result;
    }

    /**
     * HTTP get 请求
     * 
     * @param req
     * @return
     * @throws Exception
     */
    private static String getWithException(HttpReq req) throws Exception {
        log.info("get==> begin, params={}", req);
        //step1:校验参数
        if (!checkHttpReq(req)) {
            return null;
        }
        //step2:获取客户端
        CloseableHttpClient httpClient = getHttpClient();
        //step3:构建请求
        HttpGet httpGet = new HttpGet();
        //配置请求
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(1000)//连接超时时间
                .setSocketTimeout(10 * 1000)//读取超时时间
                .build();
        httpGet.setConfig(requestConfig);
        //设置请求参数
        String url = req.getUrl();
        Map<String, Object> params = req.getParams();
        String paramCharset = StringUtils.isBlank(req.getParamCharset()) ? CHARSET_UTF_8 : req.getParamCharset();
        //转换map参数
        UrlEncodedFormEntity entity = convertMapParamToUrlEncodedFormEntity(params, paramCharset);
        if (entity != null) {
            String strParams = EntityUtils.toString(entity);
            url = url + "?" + strParams;
            System.out.println(strParams);
        }
        URI uri = new URIBuilder(url).build();
        httpGet.setURI(uri);
        //step4:执行请求
        log.info("get, Executing request {}", httpGet.getRequestLine());
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
        log.info("get, httpResponse:{}", httpResponse);
        //step5:处理响应
        String result = responseHandler(httpResponse);
        log.info("get, responseBody:{}", result);
        log.info("get==> end");
        return result;

    }

    private static UrlEncodedFormEntity convertMapParamToUrlEncodedFormEntity(Map<String, Object> params, String paramCharset) throws UnsupportedEncodingException {
        if (MapUtils.isNotEmpty(params)) {
            List<NameValuePair> nvps = new ArrayList<>(params.size());
            for (Entry<String, Object> entry : params.entrySet()) {
                String value = entry.getValue() != null ? entry.getValue().toString() : null;
                NameValuePair nvp = new BasicNameValuePair(entry.getKey(), value);
                nvps.add(nvp);
            }
            return new UrlEncodedFormEntity(nvps, paramCharset);
        }
        return null;
    }

    /**
     * 处理HTTP响应
     * 
     * @param httpResponse
     * @return null if httpResponse is null or throw exception
     * @throws Exception
     */
    private static String responseHandler(CloseableHttpResponse httpResponse) throws Exception {
        if (null == httpResponse) {
            return null;
        }
        try {
            int status = httpResponse.getStatusLine().getStatusCode();
            if (status == 200) {
                HttpEntity entity = httpResponse.getEntity();
                return entity != null ? EntityUtils.toString(entity, CHARSET_UTF_8) : null;
            } else {
                log.warn("responseHandler, Unexpected response status: {}", status);
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            try {
                httpResponse.close();
            } catch (IOException e) {
                log.error("responseHandler, httpResponse.close error Msg:{}", e.getMessage(), e);
            }
        }
    }

    /**
     * 获取客户端
     * 单例模式（线程安全）
     * 
     * @return
     */
    private static CloseableHttpClient getHttpClient() {
        if (null == httpClient) {
            synchronized (HTTPUtils.class) {
                if (null == httpClient) {
                    httpClient = createHttpClient();
                }
            }
        }
        return httpClient;
    }

    /**
     * 构建客户端
     * 
     * @return
     */
    private static CloseableHttpClient createHttpClient() {
        //step1:创建连接池
        PoolingHttpClientConnectionManager phccm = new PoolingHttpClientConnectionManager();
        phccm.setMaxTotal(200);//最大连接数
        phccm.setDefaultMaxPerRoute(20);//请求每个主机的最大并发数
        //step2:创建客户端
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(phccm).build();
        return httpClient;
    }

    /**
     * 检查请求参数
     * 
     * @param req
     * @return true 通过 false 未通过
     */
    private static boolean checkHttpReq(HttpReq req) {
        if (StringUtils.isBlank(req.getUrl())) {
            log.warn("checkHttpReq, url must not be empty");
            return false;
        }
        return true;

    }

}
