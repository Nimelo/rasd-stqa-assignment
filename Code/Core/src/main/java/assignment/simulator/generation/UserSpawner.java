package assignment.simulator.generation;

import assignment.configurations.simulation.objects.UserGroup;
import assignment.simulator.generation.randomization.RNGMechanism;
import assignment.simulator.objects.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Mateusz Gasior on 03-Jan-17.
 */
public class UserSpawner {
    private Long currentId;
    private List<UserGroup> userGroups;
    private RNGMechanism rngMechanism;

    public UserSpawner(Long currentId, List<UserGroup> userGroups, RNGMechanism rngMechanism) {
        this.currentId = currentId;
        this.userGroups = userGroups;
        this.rngMechanism = rngMechanism;
    }

    public List<User> spawn() {
        List<User> users = new ArrayList<>();

        for (UserGroup userGroup : userGroups) {
            users.addAll(spawnUsersForGroup(userGroup));
        }

        return users;
    }

    public Collection<? extends User> spawnUsersForGroup(UserGroup userGroup) {
        List<User> users = new ArrayList<>();

        for (int i = 0; i < userGroup.getAmountOfMembers(); i++) {
            users.add(spawnUser(userGroup));
        }

        return  users;
    }

    public User spawnUser(UserGroup userGroup) {
        Long id = currentId++;
        //BigDecimal budget = new BigDecimal(rngMechanism.generateFromRange(userGroup.getMinBudget().doubleValue(), userGroup.getMaxBudget().doubleValue()));
        User user = new User(id, null, userGroup.getJobDistributionLambda(), userGroup.getRequestSizeDistributionLambda(), null);
        return user;
    }

}
