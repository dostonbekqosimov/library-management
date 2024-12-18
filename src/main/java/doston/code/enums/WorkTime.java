package doston.code.enums;


import lombok.Getter;

@Getter
public enum WorkTime {
    MORNING("08:00 AM - 12:00 PM"),
    AFTERNOON("12:00 PM - 04:00 PM"),
    EVENING("04:00 PM - 08:00 PM"),
    FULL_DAY("08:00 AM - 08:00 PM");

    private final String timeRange;

    WorkTime(String timeRange) {
        this.timeRange = timeRange;
    }

}
