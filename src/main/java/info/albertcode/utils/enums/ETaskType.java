package info.albertcode.utils.enums;

/**
 * @Description: 事件的分类
 * @Author: Albert Shen
 */

public enum ETaskType {
    HttpRequest(0),
    StringParser(1),
    RssGenerate(2),
    RegisterForWebPage(3);

    private Integer value;

    private ETaskType(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return this.value;
    }

    public static ETaskType valueOf(Integer value){
        for (ETaskType type : ETaskType.class.getEnumConstants()){
            if (type.getValue().equals(value)){
                return type;
            }
        }
        return null;
    }
}
