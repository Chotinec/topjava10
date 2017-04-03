package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealaoImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.CounterUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MealServlet extends HttpServlet {

    private static String INSERT_OR_EDIT = "/meal.jsp";
    private static String LIST_MEAL = "/meals.jsp";

    private MealDao dao;

    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);

    public MealServlet() {
        super();
        dao = new MealaoImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("redirect to meals");

        String forward="";
        String action = req.getParameter("action");
        switch (action) {
            case "meals":
                req.setAttribute("mealList", MealsUtil.getMealsWithExceeded());
                forward = LIST_MEAL;
                break;
            case "delete":
                int mealId = Integer.parseInt(req.getParameter("mealId"));
                dao.deleteMeal(mealId);
                req.setAttribute("mealList", MealsUtil.getMealsWithExceeded());
                forward = LIST_MEAL;
                break;
            case "edit":
                mealId = Integer.parseInt(req.getParameter("mealId"));
                Meal meal  = dao.getMealById(mealId);
                req.setAttribute("meal", MealsUtil.createWithExceed(meal));
                forward = INSERT_OR_EDIT;
                break;
            case "insert":
                forward = INSERT_OR_EDIT;
                break;
            default:
                forward = LIST_MEAL;
                break;
        }

        getServletContext().getRequestDispatcher(forward).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        LOG.debug("redirect to meals");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(req.getParameter("dateTime"), formatter);

        String mealId = req.getParameter("id");
        if(mealId == null || mealId.isEmpty()) {
            Meal meal = new Meal(CounterUtil.getCounter(),
                    dateTime,
                    req.getParameter("description"),
                    Integer.parseInt(req.getParameter("calories"))
            );
            MealWithExceed mealWithExceed = MealsUtil.createWithExceed(meal);
            dao.addMeal(mealWithExceed);
        } else {
            Meal meal = new Meal(Integer.parseInt(req.getParameter("id")),
                    dateTime,
                    req.getParameter("description"),
                    Integer.parseInt(req.getParameter("calories"))
            );
            MealWithExceed mealWithExceed = MealsUtil.createWithExceed(meal);
            dao.updateMeal(mealWithExceed);
        }

        req.setAttribute("mealList", MealsUtil.getMealsWithExceeded());
        getServletContext().getRequestDispatcher(LIST_MEAL).forward(req, resp);
    }
}
