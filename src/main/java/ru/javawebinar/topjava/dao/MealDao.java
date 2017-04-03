package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import java.util.List;

/**
 * Created by Artiom on 29.03.2017.
 */
public interface MealDao {

    void addMeal(MealWithExceed meal);
    void deleteMeal(int id);
    void updateMeal(MealWithExceed meal);
    List<Meal> getAllMeals();
    Meal getMealById(int id);
}
