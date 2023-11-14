package christmas.domain;

import christmas.config.ErrorMessage;

import java.util.Arrays;

public class Date implements CheckEventDate {
    private static final int WEEK_NUM = 7;
    private static final int WEEK_DIFF = 4;
    private static final int FIRST_DAY = 1;
    private static final int LAST_DAY = 31;
    private static final int CHRISTMAS = 25;

    private final int date;

    private Date(int date) {
        validate(date);
        this.date = date;
    }

    public static Date from(int date) {
        return new Date(date);
    }

    private void validate(int date) {
        if (date < FIRST_DAY || date > LAST_DAY) {
            throw new IllegalArgumentException(ErrorMessage.WRONG_DATE.getMessage());
        }
    }

    private enum DayOfWeek {
        SUN(0), MON(1), TUE(2), WED(3), THU(4), FRI(5), SAT(6);

        private final int value;

        DayOfWeek(int value) {
            this.value = value;
        }

        public static DayOfWeek from(int value) {
            return Arrays.stream(DayOfWeek.values())
                    .filter(it -> value == it.value)
                    .findAny()
                    .orElse(null);
        }
    }

    /**
     * 2023년 12월을 기준으로 현재 날짜에 맞는 요일을 반환합니다.
     * @return DayOfWeek (0: 일요일 ~ 6: 토요일)
     */
    private DayOfWeek getDayOfWeek() {
        int dayValue = (date + WEEK_DIFF) % WEEK_NUM;
        return DayOfWeek.from(dayValue);
    }

    @Override
    public boolean isWeekend() {
        DayOfWeek dayOfWeek = getDayOfWeek();
        return dayOfWeek == DayOfWeek.FRI || dayOfWeek == DayOfWeek.SAT;
    }

    @Override
    public boolean isSpecialDay() {
        return getDayOfWeek() == DayOfWeek.SUN || date == CHRISTMAS;
    }

    @Override
    public boolean isNotPassedChristmas() {
        return date <= CHRISTMAS;
    }

    @Override
    public int timePassedSinceFirstDay() {
        return date - FIRST_DAY;
    }

    public int getDateValue() {
        return date;
    }
}
