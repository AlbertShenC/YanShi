package info.albertcode.domain;

import info.albertcode.utils.enums.EUserGroup;

/**
 * @Description: 用户组信息
 * @Author: Albert Shen
 */

public class UserGroup {
    private EUserGroup group;
    private String Description;

    public EUserGroup getGroup() {
        return group;
    }

    public void setGroup(EUserGroup group) {
        this.group = group;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    @Override
    public String toString() {
        return "UserGroup{" +
                "group=" + group +
                ", Description='" + Description + '\'' +
                '}';
    }
}
