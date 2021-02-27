package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private MealRestController controller;
    private ConfigurableApplicationContext appCtx;

    @Override
    public void init() {
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        controller = appCtx.getBean(MealRestController.class);
    }

    @Override
    public void destroy() {
        appCtx.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        if (meal.isNew()) {
            controller.create(meal);
        } else {
            controller.update(meal);
        }
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        switch (action == null ? "all" : action) {
            case "delete" -> {
                int id = getId(request);
                log.info("Delete {}", id);
                controller.delete(id);
                response.sendRedirect("meals");
            }
            case "create", "update" -> {
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        controller.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
            }
            default -> {
                log.info("getAll");
                String dateStartParam = request.getParameter("dateStart");
                String dateEndParam = request.getParameter("dateEnd");
                String timeStartParam = request.getParameter("timeStart");
                String timeEndParam = request.getParameter("timeEnd");
                LocalDate dateStart;
                LocalDate dateEnd;
                LocalTime timeStart;
                LocalTime timeEnd;

                boolean empty = true;
                if (isNull(dateStartParam)) {
                    dateStart = LocalDate.MIN;
                } else {
                    empty = false;
                    dateStart = LocalDate.parse(dateStartParam);
                }
                if (isNull(dateEndParam)) {
                    dateEnd = LocalDate.MAX;
                } else {
                    empty = false;
                    dateEnd = LocalDate.parse(dateEndParam);
                }
                if (isNull(timeStartParam)) {
                    timeStart = LocalTime.MIN;
                } else {
                    empty = false;
                    timeStart = LocalTime.parse(timeStartParam);
                }
                if (isNull(timeEndParam)) {
                    timeEnd = LocalTime.MAX;
                } else {
                    empty = false;
                    timeEnd = LocalTime.parse(timeEndParam);
                }

                request.setAttribute("meals", empty ?
                                controller.getAll() :
                                controller.getAll(dateStart, dateEnd, timeStart, timeEnd));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
            }
        }
    }

    private boolean isNull(String dateStartParam) {
        return "".equals(dateStartParam) || dateStartParam == null;
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
