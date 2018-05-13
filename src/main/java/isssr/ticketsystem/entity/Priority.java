package isssr.ticketsystem.entity;

import javax.persistence.Table;

@Table(name = "priority")
public enum Priority {
    LOW(1),
    AVERAGE(2),
    HIGH(3),
    URGENT(4);

    private int value;

    Priority() {
    }

    Priority(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Priority parse(int id) {
        Priority Priority = null; // Default
        for (Priority item : Priority.values()) {
            if (item.getValue() == id) {
                Priority = item;
                break;
            }
        }
        return Priority;
    }
}
