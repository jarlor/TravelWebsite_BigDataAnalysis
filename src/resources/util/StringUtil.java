package resources.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    /**
     * 获取字符串中的数字
     * @param str
     * @return
     */
    public static String getNumbers(String str){
        if (str != null) {
            String regEx="[^0-9]";
            Pattern pattern = Pattern.compile(regEx);
            Matcher matcher = pattern.matcher(str);
            return matcher.replaceAll("").trim();
        }
        return "";
    }
}
