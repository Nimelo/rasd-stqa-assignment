package assignment.matchers;

import assignment.queues.enums.QueueEnum;
import assignment.queues.restrictions.QueueRestrictions;
import assignment.queues.restrictions.detailed.HugeQueueRestrictions;
import assignment.queues.restrictions.detailed.LargeQueueRestrictions;
import assignment.queues.restrictions.detailed.MediumSizedQueueRestrictions;
import assignment.queues.restrictions.detailed.ShortQueueRestriction;
import assignment.resources.hardware.HardwareResources;
import assignment.resources.monitors.ResourcesMonitor;
import assignment.resources.objects.RequestedHardware;
import assignment.resources.objects.RequestedTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Created by mrnim on 25-Dec-16.
 */
class JobToQueueMatcherTest {
    private JobToQueueMatcher matcher;
    private ResourcesMonitor resourcesMonitor;
    private QueueRestrictions queueRestrictions;

    @BeforeEach
    void setUp() {
        resourcesMonitor = new ResourcesMonitor(new HardwareResources(0L, 100L, 10L), 0L);
        queueRestrictions = new QueueRestrictions(new HugeQueueRestrictions(resourcesMonitor),
                new LargeQueueRestrictions(resourcesMonitor),
                new MediumSizedQueueRestrictions(resourcesMonitor),
                new ShortQueueRestriction(resourcesMonitor));
        matcher = new JobToQueueMatcher(queueRestrictions);
    }

    QueueEnum matchBy(Long cores, Long time){
        RequestedTime requestedTime = new RequestedTime(time);
        RequestedHardware requestedHardware = new RequestedHardware(cores, 0L, true);
        return matcher.match(requestedHardware, requestedTime);
    }
    @Test
    void matchShort_1T_2F() {
        Long cores = queueRestrictions.getShortQueueRestriction().getMaxCoresPerSingleJob() - 1L;
        Long time = queueRestrictions.getShortQueueRestriction().getMaxExecutionTime() + 1L;

        QueueEnum queueEnum = matchBy(cores, time);

        assertNotEquals(QueueEnum.SHORT, queueEnum);
    }

    @Test
    void matchShort_1T_2T() {
        Long cores = queueRestrictions.getShortQueueRestriction().getMaxCoresPerSingleJob() - 1L;
        Long time = queueRestrictions.getShortQueueRestriction().getMaxExecutionTime() - 1L;

        QueueEnum queueEnum = matchBy(cores, time);

        assertEquals(QueueEnum.SHORT, queueEnum);
    }

    @Test
    void matchShort_1F() {
        Long cores = queueRestrictions.getShortQueueRestriction().getMaxCoresPerSingleJob() + 1L;
        Long time = queueRestrictions.getShortQueueRestriction().getMaxExecutionTime() + 1L;

        QueueEnum queueEnum = matchBy(cores, time);

        assertNotEquals(QueueEnum.SHORT, queueEnum);
    }

    @Test
    void matchMS_1T_2F() {
        Long cores = queueRestrictions.getMediumSizedQueueRestrictions().getMaxCoresPerSingleJob() - 1L;
        Long time = queueRestrictions.getMediumSizedQueueRestrictions().getMaxExecutionTime() + 1L;

        QueueEnum queueEnum = matchBy(cores, time);

        assertNotEquals(QueueEnum.SHORT, queueEnum);
        assertNotEquals(QueueEnum.MEDIUM_SIZED, queueEnum);
    }

    @Test
    void matchMS_1T_2T() {
        Long cores = queueRestrictions.getMediumSizedQueueRestrictions().getMaxCoresPerSingleJob() - 1L;
        Long time = queueRestrictions.getMediumSizedQueueRestrictions().getMaxExecutionTime() - 1L;

        QueueEnum queueEnum = matchBy(cores, time);

        assertEquals(QueueEnum.MEDIUM_SIZED, queueEnum);
    }

    @Test
    void matchMS_1F() {
        Long cores = queueRestrictions.getMediumSizedQueueRestrictions().getMaxCoresPerSingleJob() + 1L;
        Long time = queueRestrictions.getMediumSizedQueueRestrictions().getMaxExecutionTime() + 1L;

        QueueEnum queueEnum = matchBy(cores, time);

        assertNotEquals(QueueEnum.SHORT, queueEnum);
        assertNotEquals(QueueEnum.MEDIUM_SIZED, queueEnum);
    }

    @Test
    void matchLARGE_1T_2F() {
        Long cores = queueRestrictions.getLargeQueueRestrictions().getMaxCoresPerSingleJob() - 1L;
        Long time = queueRestrictions.getLargeQueueRestrictions().getMaxExecutionTime() + 1L;

        QueueEnum queueEnum = matchBy(cores, time);

        assertNotEquals(QueueEnum.SHORT, queueEnum);
        assertNotEquals(QueueEnum.MEDIUM_SIZED, queueEnum);
        assertNotEquals(QueueEnum.LARGE, queueEnum);
    }

    @Test
    void matchLARGE_1T_2T() {
        Long cores = queueRestrictions.getLargeQueueRestrictions().getMaxCoresPerSingleJob() - 1L;
        Long time = queueRestrictions.getLargeQueueRestrictions().getMaxExecutionTime() - 1L;

        QueueEnum queueEnum = matchBy(cores, time);

        assertEquals(QueueEnum.LARGE, queueEnum);
    }

    @Test
    void matchLARGE_1F() {
        Long cores = queueRestrictions.getLargeQueueRestrictions().getMaxCoresPerSingleJob() + 1L;
        Long time = queueRestrictions.getLargeQueueRestrictions().getMaxExecutionTime() + 1L;

        QueueEnum queueEnum = matchBy(cores, time);

        assertNotEquals(QueueEnum.SHORT, queueEnum);
        assertNotEquals(QueueEnum.MEDIUM_SIZED, queueEnum);
        assertNotEquals(QueueEnum.LARGE, queueEnum);
    }

    @Test
    void matchHUGE() {
        Long cores = queueRestrictions.getHugeQueueRestrictions().getMaxCoresPerSingleJob() + 1L;
        Long time = queueRestrictions.getHugeQueueRestrictions().getMaxExecutionTime() + 1L;

        QueueEnum queueEnum = matchBy(cores, time);

        assertNotEquals(QueueEnum.SHORT, queueEnum);
        assertNotEquals(QueueEnum.MEDIUM_SIZED, queueEnum);
        assertNotEquals(QueueEnum.LARGE, queueEnum);
        assertEquals(QueueEnum.HUGE, queueEnum);
    }
}