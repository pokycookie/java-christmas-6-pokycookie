package christmas.domain;

import java.util.Arrays;

public enum Badge {
    SANTA("산타", 20000),
    TREE("트리", 10000),
    STAR("별", 5000),
    NOTHING("없음", 0);

    private final String badgeName;
    private final int threshold;

    Badge(String badgeName, int threshold) {
        this.badgeName = badgeName;
        this.threshold = threshold;
    }

    public static String getBadgeNameWithBenefitPrice(int benefitPrice) {
        return Arrays.stream(Badge.values())
                .filter(it -> it.threshold <= benefitPrice)
                .map(it -> it.badgeName)
                .findFirst()
                .orElse(NOTHING.badgeName);
    }
}
