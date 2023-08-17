package net.swade.permissionss.group;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class Group {
    private final String name, id, nameTagFormat, chatFormat;
    private final List<String> aliases;
    private final List<String> permissions;
    private final String inheritance;

    public List<String> getAllPermissions() {
        Group group = this;
        List<String> perms = new ArrayList<>(group.permissions);
        while (group.inheritance != null) {
            Group inheritance = GroupManager.getGroup(group.inheritance);
            perms.addAll(inheritance.permissions);
            group = inheritance;
        }
        return perms;
    }
}
