package com.gemalto.idp.mobile.fasttrack.example;

import android.content.Context;
import android.text.Html;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class Utils {

    public static CharSequence generateLogTitle(String title) {
        return Html.fromHtml("<b><font color=\"#0000FF\">" + title + "</font></b><br>");
    }

    public static CharSequence generateFailureLog(String message) {
        return Html.fromHtml("<b><font color=\"#FF0000\">" + message + "</font></b><br>");
    }

    public static CharSequence generateNormalLog(String message) {
        return Html.fromHtml("<b><font color=\"#0e1111\t\">" + message + "</font></b><br>");
    }

    public static void hideKeyboard(Context context, EditText editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
        if (editText != null && editText.getWindowToken() != null && imm != null)
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    public static void showKeyboard(Context context, EditText editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
        if (editText != null && editText.getWindowToken() != null && imm != null)
            imm.showSoftInput(editText, 0);
    }

    public static class MyLogger {

        private final static MyLogger sInstance = new MyLogger();

        public static MyLogger getsInstance() {
            return sInstance;
        }

        public void updateLog(TextView textView, String title, String message) {
            updateLogTitle(textView, title);
            updateLogMessage(textView, message);
        }

        public void updateLogTitle(TextView textView, String title) {
            if (textView != null) {
                textView.append(generateLogTitle(title));
                textView.append("\n");
            }
        }

        public void updateLogMessage(TextView textView, String message) {
            if (textView != null) {
                textView.append(message);
                textView.append("\n");
            }
        }

        public void updateLogMessage(TextView textView, String message, boolean status) {
            if (textView != null) {
                textView.append(status ? generateNormalLog(message) : generateFailureLog(message));
                textView.append("\n");
            }
        }

        public void cleanLog(TextView textView) {
            if (textView != null)
                textView.setText("");
        }
    }

    /**
     * Hex String to byte array conversion
     */
    public static final class Hex {
        /**
         * @param s Hex String
         * @return byte array
         */
        public static byte[] compress(String s) {
            int slen = s.length();
            if (slen % 2 != 0)
                throw new IllegalArgumentException("Odd length");

            byte[] bs = new byte[slen / 2];
            for (int i = 0; i < slen / 2; i++) {
                String sub = s.substring(i * 2, (i * 2) + 2);
                bs[i] = (byte) Integer.parseInt(sub, 16);
            }

            return bs;
        }

        private static final char[] CHARS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

        public static String expand(byte[] bs) {
            if (bs == null)
                return "";

            StringBuilder sb = new StringBuilder();
            for (byte b : bs) {
                char b1 = CHARS[(b & 0xF0) >> 4];
                char b2 = CHARS[b & 0x0F];
                sb.append(b1);
                sb.append(b2);
            }

            return sb.toString();
        }
    }
}
