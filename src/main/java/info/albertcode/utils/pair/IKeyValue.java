package info.albertcode.utils.pair;

import java.util.Set;

/**
 * @Description: 键值对储存方式的接口，此外，要求所有实现了此接口的类，
 * 需要提供从Json字符串进行构造的方法
 * @Author: Albert Shen
 */

public interface IKeyValue {
    public Set<String> getAllKeys();

    public Integer addKey(String key);

    public Integer addValue(String key, Object value);

    public Integer deleteKey(String key);

    public Integer deleteValue(String key, Object value);

    public void clear();

    public Integer size();

    public String toJsonString();
}
