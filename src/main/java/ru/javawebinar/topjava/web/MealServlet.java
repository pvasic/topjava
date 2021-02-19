package ru.javawebinar.topjava.web;

import org.slf4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.repository.CrudRepository;
import ru.javawebinar.topjava.util.MealsDateUtil;

import static ru.javawebinar.topjava.util.MealsUtil.*;
import static ru.javawebinar.topjava.util.TimeUtil.*;
import static ru.javawebinar.topjava.util.MealsDateUtil.*;
import static org.slf4j.LoggerFactory.getLogger;


public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final String LIST_MEALS = "/meals.jsp";
    private static final String INSERT_AND_EDIT = "/edit.jsp";
    private static final CrudRepository storage = MealsDateUtil.storage;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<MealTo> meals = filteredByStreams(storage.getAllMeals(), MIN_TIME, MAX_TIME, CALORIES_PER_DAY);
        String forward = "";
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        int mealId;

        switch (action) {
            case "delete":
                storage.deleteMeal(getMealId(req));
                forward = LIST_MEALS;
                req.setAttribute("meals", meals);
                break;
            case "edit":
                forward = INSERT_AND_EDIT;
                req.setAttribute("meal", storage.getMeal(getMealId(req)));
                break;
            default:
                forward = LIST_MEALS;
                req.setAttribute("meals", meals);
                break;
        }
        RequestDispatcher view = req.getRequestDispatcher(LIST_MEALS);
        req.setAttribute("meals", filteredByStreams(storage.getAllMeals(), MIN_TIME, MAX_TIME, CALORIES_PER_DAY));
        view.forward(req, resp);
    }

    private int getMealId(HttpServletRequest req) {
        return Integer.parseInt(req.getParameter("mealId"));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String data =  req.getParameter("data");
        String description =  req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));
        Meal meal = new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), description, calories);
        storage.addMeal(meal);
        RequestDispatcher view = req.getRequestDispatcher(LIST_MEALS);
        req.setAttribute("meals", filteredByStreams(storage.getAllMeals(), MIN_TIME, MAX_TIME, CALORIES_PER_DAY));
        view.forward(req, resp);
    }
}
