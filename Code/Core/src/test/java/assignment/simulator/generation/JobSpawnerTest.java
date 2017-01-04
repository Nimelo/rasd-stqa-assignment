package assignment.simulator.generation;

import assignment.configurations.simulation.objects.JobType;
import assignment.configurations.simulation.objects.JobTypeTuple;
import assignment.simulator.generation.randomization.RNGMechanism;
import assignment.simulator.objects.Job;
import assignment.simulator.objects.JobStatus;
import assignment.simulator.objects.RequestedResource;
import assignment.simulator.objects.User;
import assignment.simulator.objects.time.Timestamp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Mateusz Gasior on 04-Jan-17.
 */
class JobSpawnerTest {
    private JobSpawner spawner;
    private List<JobType> jobTypes;

    @BeforeEach
    void setUp() {
        jobTypes = new ArrayList<JobType>() {{
           add(new JobType() {{
               setProbabilityOfJob(1.0);
               setName("JOB");
               setMinExecutionTime(0L);
               setMaxExecutionTime(100L);
               setTuples(new ArrayList<JobTypeTuple>() {{
                   add(new JobTypeTuple() {{
                       setNodeType("NODE_S");
                       setMaximumAmountOfCores(10L);
                   }});
               }});
           }});
        }};

        spawner = new JobSpawner(jobTypes, new RNGMechanism(100L));
    }

    @Test
    void spawnJobForUser() {
        Job job = spawner.spawnJobForUser(new User(1L, null, 0.5, 0.5, null, 1L, 1L), new Timestamp(512L));

        assertEquals(new Long(1L), job.getUserId());
        assertEquals(JobStatus.SPAWNED, job.getJobStatus());
        assertEquals(new Long(0L), job.getId());

        JobType jobType = this.jobTypes.get(0);
        boolean condition = job.getExecutionTime() >= jobType.getMinExecutionTime() && job.getExecutionTime() <= jobType.getMaxExecutionTime();
        assertEquals(true, condition);

        for (RequestedResource requestedResource : job.getRequestedResourceList()) {
            for (JobTypeTuple jobTypeTuple : jobType.getTuples()) {
                if(requestedResource.getNodeType().equals(jobTypeTuple.getNodeType())) {
                    boolean cond = (requestedResource.getAmountOfCores() <= jobTypeTuple.getMaximumAmountOfCores());
                    assertEquals(true, cond);
                }
            }
        }

    }

    @Test
    void generateRequestedResource() {
        List<RequestedResource> requestedResources = spawner.generateRequestedResource(new User(1L, null, 0.5, 0.5, null, 1L, 1L), new Timestamp(0L), jobTypes.get(0));
        for (RequestedResource requestedResource : requestedResources) {
            for (JobTypeTuple jobTypeTuple : jobTypes.get(0).getTuples()) {
                if(requestedResource.getNodeType().equals(jobTypeTuple.getNodeType())) {
                    boolean cond = (requestedResource.getAmountOfCores() <= jobTypeTuple.getMaximumAmountOfCores());
                    assertEquals(true, cond);
                }
            }
        }
    }

}