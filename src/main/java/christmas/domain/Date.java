package christmas.domain;

public class Date {
    private static final int WEEK_NUM = 7;
    private static final int WEEK_DIFF = 4;

    private final int date;

    private Date(int date) {
        validate(date);
        this.date = date;
    }

    public static Date from(int date) {
        return new Date(date);
    }

    private void validate(int date) {
        if (date < 1 || date > 31) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * 2023년 12월을 기준으로 현재 날짜에 맞는 요일을 숫자로 반환합니다.
     * @return dayOfWeek (0: 일요일 ~ 6: 토요일)
     */
    private int getDayOfWeek() {
        return (date + WEEK_DIFF) % WEEK_NUM;
    }

    public boolean isWeekend() {
        int dayOfWeek = getDayOfWeek();
        return dayOfWeek == 5 || dayOfWeek == 6;
    }

    public boolean isSpecialDay() {
        return getDayOfWeek() == 0 || date == 25;
    }
}
