package assignment.tasks.objects;

/**
 * Created by mrnim on 25-Dec-16.
 */
public class Timestamps {
    private Timestamp enterQueueTimestamp;
    private Timestamp quitQueueTimestamp;
    private Timestamp endOfExecutionTimestamp;

    public Timestamps() {
    }

    public Timestamp getEnterQueueTimestamp() {
        return enterQueueTimestamp;
    }

    public void setEnterQueueTimestamp(Timestamp enterQueueTimestamp) {
        this.enterQueueTimestamp = enterQueueTimestamp;
    }

    public Timestamp getQuitQueueTimestamp() {
        return quitQueueTimestamp;
    }

    public void setQuitQueueTimestamp(Timestamp quitQueueTimestamp) {
        this.quitQueueTimestamp = quitQueueTimestamp;
    }

    public Timestamp getEndOfExecutionTimestamp() {
        return endOfExecutionTimestamp;
    }

    public void setEndOfExecutionTimestamp(Timestamp endOfExecutionTimestamp) {
        this.endOfExecutionTimestamp = endOfExecutionTimestamp;
    }
}
