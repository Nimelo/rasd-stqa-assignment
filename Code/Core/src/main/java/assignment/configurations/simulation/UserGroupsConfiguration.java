package assignment.configurations.simulation;

import assignment.configurations.simulation.objects.UserGroup;

import java.util.List;

/**
 * Created by Mateusz Gasior on 02-Jan-17.
 */
public class UserGroupsConfiguration{
    private List<UserGroup> userGroup;

    public List<UserGroup> getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(List<UserGroup> userGroup) {
        this.userGroup = userGroup;
    }
}
