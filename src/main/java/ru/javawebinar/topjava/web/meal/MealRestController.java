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

import static ru.javawebinar.topjava.util.DateTimeUtil.MAX_DATE;
import static ru.javawebinar.topjava.util.DateTimeUtil.MIN_DATE;

@Controller
public class MealRestController {
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    private final MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public List<MealWithExceed> getAll() {
        LOG.info("getAll");
        return MealsUtil.getWithExceeded(service.getAll(AuthorizedUser.id()), AuthorizedUser.getCaloriesPerDay());
    }

    public List<MealWithExceed> getAll(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        Integer userId = AuthorizedUser.id();
        LOG.info("get all filtered meal for user {}", userId);
        return MealsUtil.getWithExceeded(service.getAll(
                startDate == null ? MIN_DATE : startDate,
                startTime == null ? LocalTime.MIN : startTime,
                endDate == null ? MAX_DATE : endDate,
                endTime == null ? LocalTime.MAX : endTime,
                userId), AuthorizedUser.getCaloriesPerDay());
    }

    public MealWithExceed get(int id) {
        Integer userId = AuthorizedUser.id();
        LOG.info("get meal with id {} for userId {}", id, userId);
        Meal meal = service.get(id, userId);
        return MealsUtil.createWithExceed(meal,false);
    }

    public void delete(int id) {
        Integer userId = AuthorizedUser.id();
        LOG.info("delete {}", id);
        service.delete(id, userId);
    }

    public MealWithExceed save(MealWithExceed mealWithExceed) {
        LOG.info("save {}", mealWithExceed);
        Meal meal = new Meal(
                mealWithExceed.getId(),
                mealWithExceed.getDateTime(),
                mealWithExceed.getDescription(),
                mealWithExceed.getCalories(),
                AuthorizedUser.id());
        return MealsUtil.createWithExceed(service.save(meal), mealWithExceed.isExceed());
    }

    public void update(MealWithExceed mealWithExceed) {
        LOG.info("update {}", mealWithExceed);
        Meal meal = new Meal(
                mealWithExceed.getId(),
                mealWithExceed.getDateTime(),
                mealWithExceed.getDescription(),
                mealWithExceed.getCalories(),
                AuthorizedUser.id());
        service.update(meal, AuthorizedUser.id());
    }

}