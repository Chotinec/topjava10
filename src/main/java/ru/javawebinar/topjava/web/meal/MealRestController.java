package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class MealRestController {
    protected final Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public List<MealWithExceed> getAll() {
        LOG.info("getAll");
        return MealsUtil.getWithExceeded(service.getAll(AuthorizedUser.id()), AuthorizedUser.getCaloriesPerDay());
    }

    public List<MealWithExceed> getAll(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        LOG.info("getAll filtered");
        return MealsUtil.getWithExceeded(service.getAll(startDate, startTime, endDate, endTime, AuthorizedUser.id()), AuthorizedUser.getCaloriesPerDay());
    }

    public MealWithExceed get(int id) {
        LOG.info("get " + id);
        Meal meal = service.get(id, AuthorizedUser.id());
        return MealsUtil.createWithExceed(meal,false);
    }

    public void delete(int id) {
        LOG.info("delete " + id);
        service.delete(id, AuthorizedUser.id());
    }

    public MealWithExceed save(MealWithExceed mealWithExceed) {
        LOG.info("save " + mealWithExceed);
        Meal meal = new Meal(
                mealWithExceed.getId(),
                mealWithExceed.getDateTime(),
                mealWithExceed.getDescription(),
                mealWithExceed.getCalories(),
                AuthorizedUser.id());
        return MealsUtil.createWithExceed(service.save(meal), mealWithExceed.isExceed());
    }

    public void update(MealWithExceed mealWithExceed) {
        LOG.info("update " + mealWithExceed);
        Meal meal = new Meal(
                mealWithExceed.getId(),
                mealWithExceed.getDateTime(),
                mealWithExceed.getDescription(),
                mealWithExceed.getCalories(),
                AuthorizedUser.id());
        service.update(meal, AuthorizedUser.id());
    }

}