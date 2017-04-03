package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.CounterUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.List;

public class MealaoImpl implements MealDao {

    @Override
    public void addMeal(MealWithExceed meal) {
        MealsUtil.meals.add(new Meal(CounterUtil.getCounter(),
                meal.getDateTime(),
                meal.getDescription(),
                meal.getCalories()));
    }

    @Override
    public void deleteMeal(int id) {
        Meal meal = getMealById(id);
        MealsUtil.meals.remove(meal);
    }

    @Override
    public void updateMeal(MealWithExceed mealWithExceed) {
        deleteMeal(mealWithExceed.getId());
        addMeal(mealWithExceed);
    }

    @Override
    public List<Meal> getAllMeals() {
        return MealsUtil.meals;
    }

    @Override
    public Meal getMealById(int id) {
        return MealsUtil.meals.stream().filter(m -> m.getId() == id).findFirst().get();
    }
}
