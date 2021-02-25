package util;

import java.util.Collection;

public class CollectionUtil {
    /**
     * 获得以splitFlag分割的字符串，保留length长度
     * @param collection
     * @param splitFlag
     * @param length
     * @return
     */
    public static String getStringSplitflag(Collection<String> collection, String splitFlag, int length) {
        String result = "";
        int size = 0;
        for (String system : collection) {
            result = result + system +  splitFlag;
            size++;
            if(size >= length) {
                break;
            }
        }
        return result.substring(0, result.length() - 1);
    }
}
