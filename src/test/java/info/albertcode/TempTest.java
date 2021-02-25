package info.albertcode;

import info.albertcode.utils.pair.IKeyValue;
import info.albertcode.utils.pair.OneKeyManyValues;
import info.albertcode.utils.pair.impl.OneKeyOneValue;

/**
 * @Description:
 * @Author: Albert Shen
 */

public class TempTest {
    public static void main(String[] args) {
        IKeyValue keyValue = new OneKeyManyValues();
        keyValue.addKey("sex");
        keyValue.addValue("name", "Albert");
        keyValue.addValue("name", "Shen");
        keyValue.addValue("sex", "male");
        keyValue.addValue("sex", "female");
        keyValue.addKey("age");
        keyValue.addValue("age", 20);
        keyValue.addValue("age", 10);
        keyValue.addValue("name", "Code");
        keyValue.addValue("sex", "男");
        System.out.println(keyValue.toJsonString());

        System.out.println(new OneKeyManyValues(keyValue.toJsonString()));
    }
}
