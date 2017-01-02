package assignment.configurations.simulation;

import assignment.configurations.simulation.objects.UserGroup;

import java.util.List;

/**
 * Created by Mateusz Gasior on 02-Jan-17.
 */
public class UserGroupsConfiguration{
    private List<UserGroup> userGroups;

    public List<UserGroup> getUserGroups() {
        return userGroups;
    }

    public void setUserGroups(List<UserGroup> userGroups) {
        this.userGroups = userGroups;
    }
}
