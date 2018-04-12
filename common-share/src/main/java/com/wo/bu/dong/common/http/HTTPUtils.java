package com.wo.bu.dong.common.http;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.CodingErrorAction;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HTTPUtils {
    private HTTPUtils() {
    }

    public static String post(HttpReq req) {
        log.info("post==> begin, params={}", req);
        String url = req.getUrl();
        Map<String, Object> params = req.getParams();
        String paramCharset = req.getParamCharset();
        String contentType = req.getContentType();
        // url check
        if (StringUtils.isBlank(url)) {
            log.warn("post, url must not be empty");
            return null;
        }

        try (PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
                CloseableHttpClient httpclient = HttpClients.custom().setConnectionManager(connManager).build();) {

            ConnectionConfig connectionConfig = ConnectionConfig.custom().setMalformedInputAction(CodingErrorAction.IGNORE).setUnmappableInputAction(CodingErrorAction.IGNORE)
                    .setCharset(Consts.UTF_8).build();
            connManager.setDefaultConnectionConfig(connectionConfig);
            connManager.setMaxTotal(100);
            connManager.setDefaultMaxPerRoute(10);
            //send request
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).setConnectionRequestTimeout(5000).build();
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(requestConfig);
            //set params to entity
            if (MapUtils.isNotEmpty(req.getParams())) {
                if (StringUtils.isEmpty(req.getContentType()) || ContentType.APPLICATION_FORM_URLENCODED.getMimeType().equalsIgnoreCase(contentType)) {
                    //HTML form content type
                    List<NameValuePair> nvps = new ArrayList<>(params.size());
                    for (Entry<String, Object> entry : params.entrySet()) {
                        String value = entry.getValue() != null ? entry.getValue().toString() : null;
                        NameValuePair nvp = new BasicNameValuePair(entry.getKey(), value);
                        nvps.add(nvp);
                    }
                    httpPost.setEntity(new UrlEncodedFormEntity(nvps, contentType));
                } else if (ContentType.APPLICATION_JSON.getMimeType().equalsIgnoreCase(contentType)) {
                    //json content type
                    if (StringUtils.isNotBlank(paramCharset)) {
                        httpPost.setEntity(new StringEntity(new Gson().toJson(params), ContentType.APPLICATION_JSON.withCharset(paramCharset)));
                    } else {
                        //default charset=utf-8
                        httpPost.setEntity(new StringEntity(new Gson().toJson(params), ContentType.APPLICATION_JSON));
                    }
                }
            }
            log.info("post, Executing request {}", httpPost.getRequestLine());
            // Create a custom response handler
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
                @Override
                public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }
            };
            // execute request
            String responseBody = httpclient.execute(httpPost, responseHandler);
            log.info("post, responseBody:{}", responseBody);
            log.info("post==> end");
            return responseBody;
        } catch (ClientProtocolException e) {
            log.error("post, request exception:{}", e.getMessage(), e);
        } catch (IOException e) {
            log.error("post, request exception:{}", e.getMessage(), e);
        } catch (Exception e) {
            log.error("post, request exception:{}", e.getMessage(), e);
        }
        return null;
    }

    public static String get(HttpReq req) {
        log.info("get==> begin, params={}", req);
        String url = req.getUrl();
        Map<String, Object> params = req.getParams();
        // url check
        if (StringUtils.isBlank(url)) {
            log.warn("get, url must not be empty");
            return null;
        }

        try (PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
                CloseableHttpClient httpclient = HttpClients.custom().setConnectionManager(connManager).build();) {
            ConnectionConfig connectionConfig = ConnectionConfig.custom().setMalformedInputAction(CodingErrorAction.IGNORE).setUnmappableInputAction(CodingErrorAction.IGNORE)
                    .setCharset(Consts.UTF_8).build();
            connManager.setDefaultConnectionConfig(connectionConfig);
            connManager.setMaxTotal(100);
            connManager.setDefaultMaxPerRoute(10);
            //set params to entity
            if (MapUtils.isNotEmpty(params)) {
                StringUtils.appendIfMissing(url, "?", "?");
                StringBuilder urlBuilder = new StringBuilder(url);
                for (Entry<String, Object> entry : params.entrySet()) {
                    if (null != entry.getValue()) {
                        urlBuilder.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue().toString(), Consts.UTF_8.name())).append("&");
                    }
                }
                url = StringUtils.removeEnd(urlBuilder.toString(), "&");
            }
            //send request
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).setConnectionRequestTimeout(5000).build();
            HttpGet httpGet = new HttpGet(URI.create(url));
            httpGet.setConfig(requestConfig);
            log.info("get, Executing request {}", httpGet.getRequestLine());
            // Create a custom response handler
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
                @Override
                public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }
            };
            // execute request
            String responseBody = httpclient.execute(httpGet, responseHandler);
            log.info("get, responseBody:{}", responseBody);
            log.info("get==> end");
            return responseBody;
        } catch (ClientProtocolException e) {
            log.error("get, request exception:{}", e.getMessage(), e);
        } catch (IOException e) {
            log.error("get, request exception:{}", e.getMessage(), e);
        } catch (Exception e) {
            log.error("get, request exception:{}", e.getMessage(), e);
        }
        return null;
    }
}
