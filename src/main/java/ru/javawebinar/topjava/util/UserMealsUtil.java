package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );

        List<UserMealWithExceed> mealListWithExceed = getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(14,0), 2000);
        for (UserMealWithExceed userMealWithExceed : mealListWithExceed) {
            System.out.println(userMealWithExceed.getDescription()
                    + " " + userMealWithExceed.getDateTime()
                    + " " + userMealWithExceed.getCalories()
                    + " " + userMealWithExceed.isExceed());
        }
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        return mealList.stream().filter(s -> TimeUtil.isBetween(s.getDateTime().toLocalTime(),startTime,endTime))
                .map(s -> s.toUserMealWithExceed(caloriesPerDay))
                .collect(Collectors.toList());
    }
}
