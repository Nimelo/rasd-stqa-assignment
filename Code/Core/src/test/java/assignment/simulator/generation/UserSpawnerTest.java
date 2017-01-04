package assignment.simulator.generation;

import assignment.configurations.simulation.objects.UserGroup;
import assignment.simulator.objects.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Mateusz Gasior on 04-Jan-17.
 */
class UserSpawnerTest {
    private UserSpawner userSpawner;
    @BeforeEach
    void setUp() {
        userSpawner = new UserSpawner(new ArrayList<UserGroup>() {{
            add(new UserGroup() {{
                setAmountOfMembers(1L);
                setRequestSizeDistributionLambda(0.5);
                setJobDistributionLambda(0.5);
                setBudget(new BigDecimal(124));
            }});
        }});
    }

    @Test
    void spawn() {
        List<User> spawn = userSpawner.spawn();

        assertEquals(1, spawn.size());

        User user = spawn.get(0);

        assertUser(user);
    }

    private void assertUser(User user) {
        assertEquals(new Long(0L), user.getId());
        assertEquals(new Double(0.5), user.getJobDistributionLambda());
        assertEquals(new Double(0.5), user.getRequestSizeLambda());
        assertEquals(new BigDecimal(124), user.getBudget());
    }

    @Test
    void spawnUsersForGroup() {
        UserGroup userGroup = new UserGroup() {{
            setAmountOfMembers(1L);
            setRequestSizeDistributionLambda(0.5);
            setJobDistributionLambda(0.5);
            setBudget(new BigDecimal(124));
        }};

        List<User> users = userSpawner.spawnUsersForGroup(userGroup);

        assertEquals(1, users.size());

        User user = users.get(0);

        assertUser(user);
    }

    @Test
    void spawnUser() {
        UserGroup userGroup = new UserGroup() {{
            setAmountOfMembers(1L);
            setRequestSizeDistributionLambda(0.5);
            setJobDistributionLambda(0.5);
            setBudget(new BigDecimal(124));
        }};

        assertUser(userSpawner.spawnUser(userGroup));
    }

}