package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int MEAL_ID = START_SEQ + 2;
    public static final int USER_ID = UserTestData.USER_ID;
    public static final int NOT_FOUNT_ID = 20;

    public static final Meal meal = new Meal(MEAL_ID, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);

    public static final LocalDate NUMB_30_MAX = LocalDate.of(2020, Month.JANUARY, 30);
    public static final LocalDate NUMB_31_MIN = LocalDate.of(2020, Month.JANUARY, 31);

    public static final Meal meal1 = new Meal(START_SEQ + 3, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
    public static final Meal meal2 = new Meal(START_SEQ + 4, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
    public static final Meal meal3 = new Meal(START_SEQ + 5, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
    public static final Meal meal4 = new Meal(START_SEQ + 6, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000);
    public static final Meal meal5 = new Meal(START_SEQ + 7, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500);
    public static final Meal meal6 = new Meal(START_SEQ + 8, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);


    public static List<Meal> getMeals() {
        return Arrays.asList(meal6, meal5, meal4, meal3, meal2, meal1, meal);
    }

    public static List<Meal> getBetweenInclusiveMinTo30() {
        return Arrays.asList(meal2, meal1, meal);
    }

    public static List<Meal> getBetweenInclusive31ToMax() {
        return Arrays.asList(meal6, meal5, meal4, meal3);
    }

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2022, Month.DECEMBER, 31, 23, 23), "новая еда", 300);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(meal);
        updated.setDateTime(LocalDateTime.of(2030, Month.APRIL, 5, 5, 5));
        updated.setDescription("updated meal");
        updated.setCalories(600);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().ignoringFields("userId").isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("userId").isEqualTo(expected);
    }


}
