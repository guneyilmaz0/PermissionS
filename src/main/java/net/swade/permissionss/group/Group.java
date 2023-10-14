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
    private final List<String> inheritance;

    public List<String> getAllPermissions() {
        List<String> perms = new ArrayList<>(this.permissions);
        if (inheritance.isEmpty()){
            return perms;
        }
        for (String inheritance : this.inheritance) {
            Group group = GroupManager.getGroup(inheritance);
            assert group != null;
            perms.addAll(group.getAllPermissions());
        }
        return perms;
    }
}
