package assignment.tasks.objects;

/**
 * Created by mrnim on 25-Dec-16.
 */
public class Identification {
    private Long id;
    private Long userId;

    public Identification(Long id, Long userId) {
        this.id = id;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }
}
