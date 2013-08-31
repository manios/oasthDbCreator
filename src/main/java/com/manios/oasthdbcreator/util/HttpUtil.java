package com.manios.oasthdbcreator.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * A utility class which performs HTTP requests an returns response in
 * {@link String} objects
 */
public class HttpUtil {

    public HttpUtil() {
    }
    private final static StringBuilder sbu = new StringBuilder();
    private static final String ENCODING_UTF8 = "UTF8";
    private static final String ENCODING_WINDOWS1253 = "windows-1253";
    private static final String ENCODING_ISO8859_7 = "ISO8859_7";
    private static final int READ_BUFFER_SIZE = 8000;

    public static String post(String urlString, String postParams, long bytesToSkip) throws IOException {
        return post(urlString, postParams, bytesToSkip, false, false);

    }

    /**
     * Performs a HTTP request using GET POST method
     *
     * @param urlString Request URL
     * @param postParams Url parameters which are passed after ?. Example value:
     * a=1&b=bob
     * @param bytesToSkip Number of bytes to skip in response stream. Useful if
     * you are interested in a specific part of the response. Default value = 0
     * @param isAjax True if you want to send this request as an AJAX request
     * using X-Requested-With=XMLHttpRequest header
     * @param isMobile True if you want to perform this request using a mobile
     * phone User-Agent
     * @throws IOException throws this exception if any IO or network error
     * occurs
     */
    public static String post(String urlString, String postParams, long bytesToSkip, boolean isAjax, boolean isMobile) throws IOException {
        cleanSbu();

        // long startTime0 = System.currentTimeMillis();
        URL oracle = new URL(urlString);
        HttpURLConnection yc = (HttpURLConnection) oracle.openConnection();

        // populate Http headers
        if (isMobile) {
            populateMobileHttpHeaders(yc, urlString, isAjax);
        } else {
            populateDesktopHttpHeaders(yc, urlString, isAjax);
        }

        yc.setDoOutput(true);

        // create output POST writer
        OutputStreamWriter wr = new OutputStreamWriter(yc.getOutputStream());

        // write POST parameters
        wr.write(postParams);
        wr.flush();

        BufferedReader in = new BufferedReader(new InputStreamReader(
                yc.getInputStream(), ENCODING_UTF8), READ_BUFFER_SIZE);
        String inputLine;

        in.skip(bytesToSkip);
        while ((inputLine = in.readLine()) != null) {
            sbu.append(inputLine);
        }

        wr.close();
        in.close();

        // long endTime0 = System.currentTimeMillis();
        // Log.d(TAG, "Spent to donwload" + +(endTime0 - startTime0));

        return sbu.toString();

    }

    public static String get(String urlString, long bytesToSkip) throws IOException {
        return get(urlString, bytesToSkip, false, false);
    }

    /**
     * Performs a HTTP request using GET method
     *
     * @param urlString Request URL
     * @param urlParams Url parameters which are passed after ?. Example value:
     * a=1&b=bob
     * @param bytesToSkip Number of bytes to skip in response stream. Useful if
     * you are interested in a specific part of the response. Default value = 0
     * @param isAjax True if you want to send this request as an AJAX request
     * using X-Requested-With=XMLHttpRequest header
     * @param isMobile True if you want to perform this request using a mobile
     * phone User-Agent
     * @throws IOException throws this exception if any IO or network error
     * occurs
     */
    public static String get(String urlString, long bytesToSkip, boolean isAjax, boolean isMobile) throws IOException {
        cleanSbu();

        // long startTime0 = System.currentTimeMillis();
        URL oracle = new URL(urlString);
        HttpURLConnection yc = (HttpURLConnection) oracle.openConnection();

        // populate Http headers
        if (isMobile) {
            populateMobileHttpHeaders(yc, urlString, isAjax);
        } else {
            populateDesktopHttpHeaders(yc, urlString, isAjax);
        }

        // set POST to false   
        yc.setDoOutput(false);

        BufferedReader in = new BufferedReader(new InputStreamReader(
                yc.getInputStream(), ENCODING_UTF8), READ_BUFFER_SIZE);
        String inputLine;

        in.skip(bytesToSkip);
        while ((inputLine = in.readLine()) != null) {
            sbu.append(inputLine);
        }


        in.close();

        // long endTime0 = System.currentTimeMillis();
        // Log.d(TAG, "Spent to donwload" + +(endTime0 - startTime0));

        return sbu.toString();

    }

    private static void populateDesktopHttpHeaders(URLConnection urlCon, final String referer, boolean isAjax) {
        urlCon.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Linux i686; rv:2.0b12) Gecko/20110222 Firefox/22.0");
        urlCon.setRequestProperty("Referer", referer);
        urlCon.setRequestProperty("Accept-Language", "el-gr,el;q=0.8,en-us;q=0.5,en;q=0.3");
        urlCon.setRequestProperty("Accept-Charset", "ISO-8859-7,utf-8;q=0.7,*;q=0.7");

        if (isAjax) {
            urlCon.setRequestProperty("X-Requested-With", "XMLHttpRequest");
        }
    }

    private static void populateMobileHttpHeaders(URLConnection urlCon, final String referer, boolean isAjax) {
        urlCon.setRequestProperty("User-Agent", UserAgentUtil.getRandomMobileUserAgent());
        urlCon.setRequestProperty("Referer", referer);
        urlCon.setRequestProperty("Accept-Language", "el-gr,el;q=0.8,en-us;q=0.5,en;q=0.3");
        urlCon.setRequestProperty("Accept-Charset", "ISO-8859-7,utf-8;q=0.7,*;q=0.7");

        if (isAjax) {
            urlCon.setRequestProperty("X-Requested-With", "XMLHttpRequest");
        }
    }

    private static void cleanSbu() {
        sbu.delete(0, sbu.length());
    }
}
