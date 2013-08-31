package com.manios.oasthdbcreator.util;

import java.util.Random;

/**
 *
 * @author user
 */
public class UserAgentUtil {

    public static final String MOBILE_USER_AGENTS[] = {
        "Mozilla/5.0 (Linux; Android 4.1.2; GT-I9300 Build/JZO54K) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.63 Mobile Safari/537.36 OPR/15.0.1162.61541",
        "Mozilla/5.0 (Linux; U; Android 4.1.2; el-gr; GT-P5100 Build/JZO54K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Safari/534.30",
        "Mozilla/5.0 (Linux; U; Android 4.1.1; el-gr; HTC_One_S Build/JRO03C) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30",
        "Mozilla/5.0 (Linux; U; Android 4.1.2; el-gr; GT-P3110 Build/JZO54K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Safari/534.30",
        "Mozilla/5.0 (Linux; U; Android 4.0.4; el-gr; SonyST21i2 Build/11.0.A.6.5) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30",
        "Mozilla/5.0 (compatible; MSIE 9.0; Windows Phone OS 7.5; Trident/5.0; IEMobile/9.0; SAMSUNG; GT-I8350)"};

    /**
     * Returns a random mobile browser user agent String from
     * {@link #MOBILE_USER_AGENTS} array.
     *
     */
    public static String getRandomMobileUserAgent() {

        return MOBILE_USER_AGENTS[new Random().nextInt(MOBILE_USER_AGENTS.length)];
    }
}
