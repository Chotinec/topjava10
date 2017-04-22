package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Objects;

import static ru.javawebinar.topjava.UserTestData.USER_ID;

public class MealTestData {

    public static final LocalDateTime START_DATE_TIME = LocalDateTime.of(2015, Month.MAY, 30, 10, 0);
    public static final LocalDateTime END_DATE_TIME = LocalDateTime.of(2015, Month.MAY, 30, 20, 0);

    public static final Meal MEAL_1 = new Meal(USER_ID+2, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500);
    public static final Meal MEAL_2 = new Meal(USER_ID+3, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000);
    public static final Meal MEAL_3 = new Meal(USER_ID+4, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500);
    public static final Meal MEAL_4 = new Meal(USER_ID+5, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 500);
    public static final Meal MEAL_5 = new Meal(USER_ID+6, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 1000);
    public static final Meal MEAL_6 = new Meal(USER_ID+7, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 500);

    public static final ModelMatcher<Meal> MATCHER = new ModelMatcher<>(
            (expected, actual) -> expected == actual ||
                    (Objects.equals(expected.getDateTime(), actual.getDateTime())
                            && Objects.equals(expected.getId(), actual.getId())
                            && Objects.equals(expected.getDescription(), actual.getDescription())
                            && Objects.equals(expected.getCalories(), actual.getCalories())
            )
    );

}
