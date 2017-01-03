package assignment.validators.configurations.simulation;

import assignment.configurations.simulation.*;
import assignment.configurations.simulation.objects.*;
import assignment.io.readers.configurations.simulation.ConfigurationReader;
import assignment.validators.ValidationException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Mateusz Gasior on 03-Jan-17.
 */
class ConfigurationValidatorTest {
    private static ConfigurationValidator configurationValidator;
    private static SystemResources systemResources;

    @BeforeAll
    static void setUp() {
        configurationValidator = new ConfigurationValidator();
        systemResources = new SystemResources();
        systemResources.setNodes(new ArrayList<Node>() {{
            add(new Node() {{
                setName("NODE_S");
                setAmount(16L);
                setAmountOfCores(16L);
                setPrice(new BigDecimal(13L));
            }});
        }});
    }

    @Test
    void validate() throws FileNotFoundException, ValidationException {
        String path = getClass().getClassLoader().getResource("test-configuration.json").getPath();
        ConfigurationReader configurationReader = new ConfigurationReader();
        Configuration configuration = configurationReader.readFromPath(path);

        configurationValidator.validate(configuration);
    }

    @Test
    void validateReservationInQueues() throws ValidationException {
        QueuesConfiguration queuesConfiguration = new QueuesConfiguration() {{
            setQueues(new ArrayList<QueueProperties>() {{
                add(new QueueProperties() {{
                    setName("QUEUE");
                    setDescription("");
                    setMaximumExecutionTime(13L);
                    setPriceFactor(0.5D);
                    setReservedResources(new ArrayList<ReservedResource>() {{
                        add(new ReservedResource() {{
                            setNodeType("NODE_S");
                            setAmount(1L);
                        }});
                    }});
                    setConstraintResources(new ArrayList<ConstraintResource>() {{
                        add(new ConstraintResource() {{
                            setNodeType("NODE_S");
                            setAmount(1L);
                            setAmountOfCores(1L);
                        }});
                    }});
                    setAvailabilityTime(new AvailabilityTime() {{
                        setBegin(new ShiftTime() {{
                            setDayOfWeek(DayOfWeek.MONDAY);
                        }});
                        setEnd(new ShiftTime() {{
                            setDayOfWeek(DayOfWeek.FRIDAY);
                        }});
                    }});
                }});
            }});
        }};

        configurationValidator.validateReservationInQueues(queuesConfiguration, systemResources);
    }

    @Test
    void validateSingleNode() throws ValidationException {
        configurationValidator.validateSingleNode("NODE_S", 1L, systemResources.getNodes());

        assertThrows(ValidationException.class, () -> {
            configurationValidator.validateSingleNode("NODE_S", 124324L, systemResources.getNodes());
        });
    }

    @Test
    void validateNodesInQueues() throws ValidationException {
        QueuesConfiguration queuesConfiguration = new QueuesConfiguration() {{
            setQueues(new ArrayList<QueueProperties>() {{
                add(new QueueProperties() {{
                    setName("QUEUE");
                    setReservedResources(new ArrayList<ReservedResource>() {{
                        add(new ReservedResource() {{
                            setNodeType("NODE_S");
                        }});
                    }});
                    setConstraintResources(new ArrayList<ConstraintResource>() {{
                        add(new ConstraintResource() {{
                            setNodeType("NODE_S");
                        }});
                    }});
                }});
            }});
        }};

        configurationValidator.validateNodesInQueues(queuesConfiguration, systemResources);

        assertThrows(ValidationException.class, () -> {
            queuesConfiguration.getQueues().get(0).getReservedResources().add(new ReservedResource() {{
              setNodeType("INCORRECT");
            }});

            configurationValidator.validateNodesInQueues(queuesConfiguration, systemResources);
        });
    }

    @Test
    void validateNodesInJobTypes() throws ValidationException {
        JobTypesConfiguration jobTypesConfiguration = new JobTypesConfiguration() {{
            setJobTypes(new ArrayList<JobType>() {{
                add(new JobType() {{
                    setTuples(new ArrayList<JobTypeTuple>() {{
                        add(new JobTypeTuple() {{
                            setNodeType("NODE_S");
                        }});
                    }});
                }});
            }});
        }};

        configurationValidator.validateNodesInJobTypes(jobTypesConfiguration, systemResources);

        assertThrows(ValidationException.class, () -> {
           jobTypesConfiguration.getJobTypes().get(0).getTuples().add(new JobTypeTuple() {{
               setNodeType("INCORRECT");
           }});

           configurationValidator.validateNodesInJobTypes(jobTypesConfiguration, systemResources);
        });
    }

    @Test
    void validateJobDistribution() throws ValidationException {
        configurationValidator.validateJobDistribution(0.5D);

        assertThrows(ValidationException.class, () -> {
            configurationValidator.validateJobDistribution(null);
        });
    }

    @Test
    void validateUserGroupsConfiguration() throws ValidationException {
        UserGroupsConfiguration userGroupsConfiguration = new UserGroupsConfiguration() {{
           setUserGroups(new ArrayList<UserGroup>() {{
               add(new UserGroup() {{
                   setAmountOfMembers(10L);
                   setMinBudget(new BigDecimal(10));
                   setMaxBudget(new BigDecimal(10));
               }});
           }});
        }};

        configurationValidator.validateUserGroupsConfiguration(userGroupsConfiguration);
    }

    @Test
    void validateQueuesConfiguration() throws ValidationException {
        QueuesConfiguration queuesConfiguration = new QueuesConfiguration() {{
            setQueues(new ArrayList<QueueProperties>() {{
                add(new QueueProperties() {{
                    setName("QUEUE");
                    setDescription("");
                    setMaximumExecutionTime(13L);
                    setPriceFactor(0.5D);
                    setReservedResources(new ArrayList<ReservedResource>() {{
                        add(new ReservedResource() {{
                            setNodeType("NODE_S");
                            setAmount(1L);
                        }});
                    }});
                    setConstraintResources(new ArrayList<ConstraintResource>() {{
                        add(new ConstraintResource() {{
                            setNodeType("NODE_S");
                            setAmount(0L);
                            setAmountOfCores(0L);
                        }});
                    }});
                    setAvailabilityTime(new AvailabilityTime() {{
                        setBegin(new ShiftTime() {{
                            setDayOfWeek(DayOfWeek.MONDAY);
                            setHours(1L);
                            setMinutes(0L);
                        }});
                        setEnd(new ShiftTime() {{
                            setDayOfWeek(DayOfWeek.FRIDAY);
                            setHours(1L);
                            setMinutes(0L);
                        }});
                    }});
                }});
            }});
        }};

        configurationValidator.validateQueuesConfiguration(queuesConfiguration);
    }

    @Test
    void validateReservedResources() throws ValidationException {
        ArrayList<ReservedResource> reservedResources = new ArrayList<ReservedResource>() {{
            add(new ReservedResource() {{
                setNodeType("NODE_S");
                setAmount(0L);
            }});
        }};

        configurationValidator.validateReservedResources(reservedResources, "");

        assertThrows(ValidationException.class, () -> {
           reservedResources.get(0).setAmount(null);
            configurationValidator.validateReservedResources(reservedResources, "");
        });
    }

    @Test
    void validateMachineOperationalCost() throws ValidationException {
        configurationValidator.validateMachineOperationalCost(new BigDecimal(1L));

        assertThrows(ValidationException.class, () -> {
            configurationValidator.validateMachineOperationalCost(new BigDecimal(0L));
        });
    }

    @Test
    void validateSystemResources() throws ValidationException {
        configurationValidator.validateSystemResources(systemResources);
    }

    @Test
    void validateSimulationTime() throws ValidationException {
        Calendar instance = Calendar.getInstance();
        Date begin = instance.getTime();
        instance.add(Calendar.MONTH, 1);
        Date end = instance.getTime();
        SimulationTime simulationTime = new SimulationTime() {{
            setBegin(begin);
            setEnd(end);
        }};

        configurationValidator.validateSimulationTime(simulationTime);
    }

    @Test
    void validateJobTypesConfiguration() throws ValidationException {
        JobTypesConfiguration jobTypesConfiguration = new JobTypesConfiguration() {{
            setJobTypes(new ArrayList<JobType>() {{
                add(new JobType() {{
                    setProbabilityOfJob(1.0D);
                    setMinExecutionTime(10L);
                    setMaxExecutionTime(10L);
                    setTuples(new ArrayList<JobTypeTuple>() {{
                        add(new JobTypeTuple() {{
                            setNodeType("NODE_S");
                            setProbabilityOfOccurrence(0.1D);
                            setMaximumAmountOfNodes(10L);
                        }});
                    }});
                }});
            }});
        }};

        configurationValidator.validateJobTypesConfiguration(jobTypesConfiguration);
    }

    @Test
    void validateRNGSeed() throws ValidationException {
        configurationValidator.validateRNGSeed(new Long(0L));
    }

}