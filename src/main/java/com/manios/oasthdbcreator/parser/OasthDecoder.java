package com.manios.oasthdbcreator.parser;

public class OasthDecoder {

    private final static String e = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";

    public static String aniMarker(String a) {
        String z = a + "";
        z = z.replaceAll("(?i)grZNn", "=");
        z = decodeCoordinates(z);
        z = z.replace('!', '3');
        z = decodeCoordinates(z);
        return z;
    }

    private static String decodeCoordinates(String a) {

        StringBuilder b = new StringBuilder("");
        char c, chr2, chr3;
        int d, enc2, enc3, enc4;
        int i = 0;
        a = a.replaceAll("[^A-Za-z0-9+/=]", "");
        while (i < a.length()) {
            d = e.indexOf(a.charAt(i++));
            enc2 = e.indexOf(a.charAt(i++));
            enc3 = e.indexOf(a.charAt(i++));
            enc4 = e.indexOf(a.charAt(i++));
            c = (char) ((d << 2) | (enc2 >> 4));
            chr2 = (char) (((enc2 & 15) << 4) | (enc3 >> 2));
            chr3 = (char) (((enc3 & 3) << 6) | enc4);
            b.append(c);
            if (enc3 != 64) {
                b.append(chr2);
            }
            if (enc4 != 64) {
                b.append(chr3);
            }
        }
        b = g(b);
        return b.toString();
    }

    private static StringBuilder g(StringBuilder a) {
        StringBuilder b = new StringBuilder("");
        int i = 0;
        char c;
        char c2;
        char c3;

        while (i < a.length()) {
            c = a.charAt(i);
            if (c < 128) {
                b.append(c);
                i++;
            } else if ((c > 191) && (c < 224)) {
                c2 = a.charAt(i + 1);
                b.append((char) (((c & 31) << 6) | (c2 & 63)));
                i += 2;
            } else {
                c2 = a.charAt(i + 1);
                c3 = a.charAt(i + 2);
                b.append((char) (((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63)));
                i += 3;
            }
        }
        return b;
    }
}
