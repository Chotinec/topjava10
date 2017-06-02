package ru.javawebinar.topjava.web.meal;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@Controller
@RequestMapping(value = "/meals")
public class JspMealController extends AbstactMealController {

    @RequestMapping(method = RequestMethod.GET)
    public String meals(Model model) {
        model.addAttribute("meals", super.getAll());
        return "meals";
    }

    @RequestMapping(value = "/delete")
    public String deleteMeal(HttpServletRequest request) {
        int id = Integer.valueOf(request.getParameter("id"));
        super.delete(id);
        return "redirect:/meals";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String newMeal(Model model) {
        final Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) ;
        model.addAttribute("meal", meal);
        return "meal";
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String getMeal(HttpServletRequest request, Model model) {
        int id = Integer.valueOf(request.getParameter("id"));
        Meal meal = super.get(id);
        model.addAttribute("meal", meal);
        return "meal";
    }

    @RequestMapping(value = "/meal", method = RequestMethod.POST)
    public String createMeal(HttpServletRequest request) {
        final Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")));

        if (request.getParameter("id").isEmpty()) {
           super.save(meal);
        } else {
            meal.setId(Integer.valueOf(request.getParameter("id")));
            super.update(meal);
        }
        return "redirect:/meals";
    }


    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    public String filterMeal(HttpServletRequest request, Model model) {
        LocalDate startDate = DateTimeUtil.parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = DateTimeUtil.parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = DateTimeUtil.parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = DateTimeUtil.parseLocalTime(request.getParameter("endTime"));

        model.addAttribute("meals", super.getBetweenDates(
                        startDate != null ? startDate : DateTimeUtil.MIN_DATE,
                        endDate != null ? endDate : DateTimeUtil.MAX_DATE,
                        startTime != null ? startTime : LocalTime.MIN,
                        endTime != null ? endTime : LocalTime.MAX
        ));

        return "redirect:/meals";
    }
}
