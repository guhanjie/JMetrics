package com.wanlitong.jmetrics.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.StopWatch;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("deprecation")
public final class HttpUtils {

    private static final Logger   logger             = LoggerFactory.getLogger(HttpUtils.class);
    private static final String[] HTTP_IP_HEADER     = { "x-forwarded-for", "Proxy-Client-IP" };    // 客户端IP查找顺序
    private static final String   FORM_ENTITY_ENCODE = "UTF-8";

    public static class SimpleHttpResult {
        private String body;
        private int    status;
        private Exception    e;

        public SimpleHttpResult(){
        }
        
        public SimpleHttpResult(int status, String body){
            this.status = status;
            this.body = body;
        }
        
        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
        
        public Exception getE() {
            return e;
        }
        
        public void setE(Exception e) {
            this.e = e;
        }
    }

    /**
     * 通过HttpClient发送get请求，并返回response的文本内容和状态码status code
     * 
     * @param url
     * @param header
     * @return
     */
    public static SimpleHttpResult doGet(String url, Header header) {
        // HttpClient请求开始
        StopWatch watch = new StopWatch();
        watch.start();
        logger.info("http client get started, url: " + url);

        // 发送HttpClient请求
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader(header);
        HttpResponse response = null;
        String output = null;
        SimpleHttpResult result = new SimpleHttpResult();
        try {
            response = httpClient.execute(httpGet);
            output = EntityUtils.toString(response.getEntity());
            result.setBody(output);
            result.setStatus(response.getStatusLine().getStatusCode());
        } catch (ClientProtocolException e) {
            logger.error("http client error", e);
        } catch (IOException e) {
            logger.error("http client error", e);
        } finally {
            httpClient.getConnectionManager().shutdown();
        }

        // HttpClient请求结束
        watch.stop();
        logger.debug("http client get successed, cost time: " + watch.getTime() + ", output: " + output);

        return result;
    }

    /**
     * 通过HttpClient发送get请求，并返回response的文本内容
     * 
     * @param url
     * @return
     */
    public static SimpleHttpResult doGet(String url) {
        int status = 0;
        String body = null;
        
        // HttpClient请求开始
        StopWatch watch = new StopWatch();
        watch.start();
        logger.info("http client get started, url: {}", url);
        
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        
        try {
            HttpResponse response = httpClient.execute(httpGet);
            status = response.getStatusLine().getStatusCode();
            body = EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            if(status == 0){
                status = 500;
            }
            body = e.getMessage();
            logger.error("http get error. url="+url,e);
        } finally {
            httpClient.getConnectionManager().shutdown();
        }

        // HttpClient请求结束
        watch.stop();
        logger.debug("http get end. status{} ,time:{},", status, watch.getTime());

        return new SimpleHttpResult(status, body);
    }

    /**
     * 通过HttpClient发送post请求，并返回response的文本内容
     * 
     * @param url
     * @param params
     * @return
     */
    public static String doPost(String url, Map<String, String> params, Map<String, String> header) {

        // HttpClient请求开始
        StopWatch watch = new StopWatch();
        watch.start();
        logger.info("http client post started, url: " + url);

        // params
        List<NameValuePair> formParams = new ArrayList<NameValuePair>();
        if (params != null && params.size() > 0) {
            //formParams = new ArrayList<NameValuePair>();
            for (Map.Entry<String, String> e : params.entrySet()) {
                formParams.add(new BasicNameValuePair(e.getKey(), e.getValue()));
            }
        }

        // 发送HttpClient请求
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
       
        //header
        if(header!=null && header.size()>0){
            for (String name:header.keySet()) {
                httpPost.addHeader(name, header.get(name));
            }
        }
        
        UrlEncodedFormEntity entity = null;
        try {
            entity = new UrlEncodedFormEntity(formParams, FORM_ENTITY_ENCODE);
        } catch (UnsupportedEncodingException e) {
            logger.error(FORM_ENTITY_ENCODE + " unsupported", e);
        }
        httpPost.setEntity(entity);

        HttpResponse response = null;
        String output = null;
        try {
            response = httpClient.execute(httpPost);
            output = EntityUtils.toString(response.getEntity());
        } catch (ClientProtocolException e) {
            logger.error("http client error", e);
        } catch (IOException e) {
            logger.error("http client error", e);
        } finally {
            httpClient.getConnectionManager().shutdown();
        }

        // HttpClient请求结束
        watch.stop();
        logger.info("http client post successed, cost time: " + watch.getTime() + ", output: " + output);

        return output;
    }

    /**
     * 获取客户端IP
     * 
     * @param request
     * @return
     */
    public static String getClientIp(HttpServletRequest request) {
        if (null == request) {
            return null;
        }
        String ip = null;
        for (String key : HTTP_IP_HEADER) {
            ip = request.getHeader(key);
            if (StringUtils.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
                break;
            }
        }
        // default
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip == null ? "" : ip.split(",")[0];
    }
    

    public static Map<String, String> getCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        Map<String,String> map = new HashMap<String,String>();
        if(cookies == null || cookies.length == 0){
            return map;
        }
        for(Cookie cookie : cookies){
            map.put(cookie.getName(), cookie.getValue());
        }
        return map;
    }
    
    
    public static String getCookie(HttpServletRequest request, String name) {
        if(name == null){
            return null;
        }
        
        Cookie[] cookies = request.getCookies();
        if(cookies == null || cookies.length == 0){
            return null;
        }
        
        for(Cookie cookie : cookies){
            if(name.equals(cookie.getName())){
                return cookie.getValue();
            }
        }
        return null;
    }

    /**
     * 获取referer
     * 
     * @param request
     * @return
     */
    public static String getHttpReferr(HttpServletRequest request) {
        return request.getHeader("referer");
    }
    
    public static int getResponseCode(String url) throws IOException {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse response = httpclient.execute(httpGet);
            return response.getStatusLine().getStatusCode();
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
    }

    public static String getRequestBody(HttpServletRequest request, String charsetName) throws ServletException {
        HttpServletRequestWrapper requestWrapper = new HttpServletRequestWrapper(request);
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;
        try {
            InputStream inputStream = requestWrapper.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream, charsetName));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) != -1) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            }
        } catch (IOException ex) {
            throw new ServletException("Error reading the request payload", ex);
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException iox) {
                    // ignore
                }
            }
        }
        return stringBuilder.toString();
    }

    public static HttpResponse sendDelete(String url) throws IOException {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        try {
            HttpDelete httpDelete = new HttpDelete(url);
            return httpclient.execute(httpDelete);
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
    }

    public static HttpResponse sendDelete(String url, String username, String password) throws IOException {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        try {
            HttpDelete httpDelete = new HttpDelete(url);
            String s = username + ":" + password;
            String basicHeader = "Basic " + Base64.encodeBase64String(s.getBytes());
            httpDelete.setHeader(HttpHeaders.AUTHORIZATION, basicHeader);
            return httpclient.execute(httpDelete);
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
    }

    public static boolean isAjaxRequest(HttpServletRequest request) {
        return "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"));
    }

    public static boolean isMultiPartRequest(HttpServletRequest request) {
        return request.getHeader(HTTP.CONTENT_TYPE) != null && request.getHeader(HTTP.CONTENT_TYPE).contains("multipart/form-data");
    }

    public static String encodeUTF8(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (Exception e) {
            return str;
        }
    }

    public static String decodeUTF8(String str) {
        try {
            return URLDecoder.decode(str, "UTF-8");
        } catch (Exception e) {
            return str;
        }
    }
}
