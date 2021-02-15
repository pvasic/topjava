package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.MealTo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;
import ru.javawebinar.topjava.util.TimeUtil;


import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.*;
import static ru.javawebinar.topjava.util.MealsDataUtil.*;


public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static List<MealTo> meals = filteredByStreams(getMEALS(), LocalTime.of(0, 0), LocalTime.of(23, 59), getCaloriesPerDay());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("meals", meals);
        req.getRequestDispatcher("/meals.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
