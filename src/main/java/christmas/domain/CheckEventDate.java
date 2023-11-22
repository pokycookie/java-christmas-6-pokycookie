package christmas.domain;

public interface CheckEventDate {
    boolean isWeekend();
    boolean isSpecialDay();
    boolean isNotPassedChristmas();
    int timePassedSinceFirstDay();
}
