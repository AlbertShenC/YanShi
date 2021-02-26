package info.albertcode;

import info.albertcode.utils.pair.OneKeyManyValues;
import info.albertcode.utils.pair.OneKeyOneValue;

/**
 * @Description:
 * @Author: Albert Shen
 */

public class TempTest {
    public static void main(String[] args) {
        OneKeyOneValue keyValue = new OneKeyOneValue();
        keyValue.addValue("name", "Albert");
        keyValue.addValue("sex", "male");
        keyValue.addValue("age", 20);
        System.out.println(keyValue.toJsonString());
    }
}
