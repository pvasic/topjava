package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.InMemoryRepository;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

public class MealsDataUtil {
    private static final int CALORIES_PER_DAY = 2000;

    private static final InMemoryRepository storage = new InMemoryRepository();

    private static List<Meal> getMeals() {
        storage.addMeal(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        storage.addMeal(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        storage.addMeal(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        storage.addMeal(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        storage.addMeal(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        storage.addMeal(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        storage.addMeal(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 41));
        return storage.getAllMeals();
    }
}