package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collection;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService mealService;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void get() throws Exception {
        Meal meal = mealService.get(USER_ID+2, USER_ID);
        MATCHER.assertEquals(MEAL_1, meal);
    }

    @Test
    public void delete() throws Exception {
        mealService.delete(MEAL_1.getId(), USER_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(
                MEAL_6,
                MEAL_5,
                MEAL_4,
                MEAL_3,
                MEAL_2
        ), mealService.getAll(USER_ID));
    }

    @Test
    public void getBetweenDateTimes() throws Exception {
        Collection<Meal> betweenDateTimeMeals = mealService.getBetweenDateTimes(START_DATE_TIME, END_DATE_TIME, USER_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(
                MEAL_3,
                MEAL_2,
                MEAL_1), betweenDateTimeMeals);
    }

    @Test
    public void getAll() throws Exception {
        Collection<Meal> allMeals = mealService.getAll(USER_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(
                MEAL_6,
                MEAL_5,
                MEAL_4,
                MEAL_3,
                MEAL_2,
                MEAL_1
        ), allMeals);
    }

    @Test
    public void update() throws Exception {
        Meal updatedMeal = new Meal(MEAL_1);
        updatedMeal.setCalories(250);
        updatedMeal.setDescription("newFood");
        mealService.update(updatedMeal, USER_ID);
        MATCHER.assertEquals(updatedMeal, mealService.get(USER_ID+2, USER_ID));
    }

    @Test
    public void save() throws Exception {
        Meal newMeal = new Meal(LocalDateTime.of(2017, Month.MAY, 30, 10, 0), "testMeal", 1000);
        Meal createdMeal = mealService.save(newMeal, USER_ID);
        newMeal.setId(createdMeal.getId());
        MATCHER.assertCollectionEquals(Arrays.asList(
                newMeal,
                MEAL_6,
                MEAL_5,
                MEAL_4,
                MEAL_3,
                MEAL_2,
                MEAL_1
        ), mealService.getAll(USER_ID));
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFound() throws Exception {
        mealService.get(MEAL_1.getId(), ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundDelete() throws Exception {
        mealService.delete(1, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundUpdate() throws Exception {
        Meal updatedMeal = new Meal(MEAL_1);
        updatedMeal.setDescription("testDescription");
        mealService.update(updatedMeal, ADMIN_ID);
    }
}