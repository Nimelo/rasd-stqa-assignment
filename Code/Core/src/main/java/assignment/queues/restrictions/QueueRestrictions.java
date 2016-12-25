package assignment.queues.restrictions;

import assignment.queues.restrictions.detailed.HugeQueueRestrictions;
import assignment.queues.restrictions.detailed.LargeQueueRestrictions;
import assignment.queues.restrictions.detailed.MediumSizedQueueRestrictions;
import assignment.queues.restrictions.detailed.ShortQueueRestriction;

/**
 * Created by mrnim on 25-Dec-16.
 */
public class QueueRestrictions {
    private HugeQueueRestrictions hugeQueueRestrictions;
    private LargeQueueRestrictions largeQueueRestrictions;
    private MediumSizedQueueRestrictions mediumSizedQueueRestrictions;
    private ShortQueueRestriction shortQueueRestriction;

    public QueueRestrictions(HugeQueueRestrictions hugeQueueRestrictions, LargeQueueRestrictions largeQueueRestrictions, MediumSizedQueueRestrictions mediumSizedQueueRestrictions, ShortQueueRestriction shortQueueRestriction) {
        this.hugeQueueRestrictions = hugeQueueRestrictions;
        this.largeQueueRestrictions = largeQueueRestrictions;
        this.mediumSizedQueueRestrictions = mediumSizedQueueRestrictions;
        this.shortQueueRestriction = shortQueueRestriction;
    }

    public HugeQueueRestrictions getHugeQueueRestrictions() {
        return hugeQueueRestrictions;
    }

    public LargeQueueRestrictions getLargeQueueRestrictions() {
        return largeQueueRestrictions;
    }

    public MediumSizedQueueRestrictions getMediumSizedQueueRestrictions() {
        return mediumSizedQueueRestrictions;
    }

    public ShortQueueRestriction getShortQueueRestriction() {
        return shortQueueRestriction;
    }
}

