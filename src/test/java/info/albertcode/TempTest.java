package info.albertcode;

import info.albertcode.utils.json.KeyValues;

/**
 * @Description:
 * @Author: Albert Shen
 */

public class TempTest {
    public static void main(String[] args) {
        KeyValues keyValues = new KeyValues();
        keyValues.addValue("name", "Albert");
        keyValues.addValue("name", "Shen");
        keyValues.addValue("sex", "male");
        keyValues.addValue("address", "Beijing");
        keyValues.addValue("sex", "female");
        keyValues.addValue("age", 10);
        System.out.println(keyValues);

        keyValues.deleteKey("sex");
        System.out.println(keyValues);

        keyValues.deleteKey("name");
        System.out.println(keyValues);
    }
}
