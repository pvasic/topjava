package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface CrudRepository {
    public Meal getMeal(int id);

    public void addMeal(Meal meal);

    public void deleteMeal(int id);

    public void updateMeal(Meal meal);

    public List<Meal> getAllMeals();
}
