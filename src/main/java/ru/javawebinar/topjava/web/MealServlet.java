package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.MealRepositoryImpl;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class MealServlet extends HttpServlet {

    private static String INSERT_OR_EDIT = "/meal.jsp";
    private static String LIST_MEAL = "/meals.jsp";

    private MealRepository repository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        repository = new MealRepositoryImpl();
    }

    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("redirect to meals");

        String forward="";
        String action = req.getParameter("action");
        switch (action) {
            case "meals":
                LOG.info("Get all");
                req.setAttribute("mealList", MealsUtil.getMealsWithExceeded(repository.getAll()));
                forward = LIST_MEAL;
                break;
            case "delete":
                int mealId = Integer.parseInt(req.getParameter("mealId"));
                LOG.info("Delete {}", mealId);
                repository.delete(mealId);
                req.setAttribute("mealList", MealsUtil.getMealsWithExceeded(repository.getAll()));
                forward = LIST_MEAL;
                break;
            case "edit":
                mealId = Integer.parseInt(req.getParameter("mealId"));
                LOG.info("Edit {}", mealId);
                Meal meal  = repository.get(mealId);
                req.setAttribute("meal", meal);
                forward = INSERT_OR_EDIT;
                break;
            case "insert":
                LOG.info("Add new meal");
                forward = INSERT_OR_EDIT;
                req.setAttribute("meal",new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000));
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
        LOG.debug("Insert or edit");

        LocalDateTime dateTime = LocalDateTime.parse(req.getParameter("dateTime"));
        String mealId = req.getParameter("id");
        Meal meal = new Meal(dateTime,
                req.getParameter("description"),
                Integer.parseInt(req.getParameter("calories"))
        );
        if(mealId == null || mealId.isEmpty()) {
            repository.save(meal);
        } else {
            meal.setId(Integer.valueOf(mealId));
            repository.save(meal);
        }

        req.setAttribute("mealList", MealsUtil.getMealsWithExceeded(repository.getAll()));
        getServletContext().getRequestDispatcher(LIST_MEAL).forward(req, resp);
    }
}
