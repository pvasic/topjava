package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryRepository implements CrudRepository {
    private static final AtomicInteger START_COUNTER = new AtomicInteger(0);

    private static AtomicInteger counter = START_COUNTER;

    private static ConcurrentMap<Integer, Meal> storage = new ConcurrentHashMap<>();

    @Override
    public Meal getMeal(int id) {
        return storage.get(id);
    }

    @Override
    public void addMeal(Meal meal) {
        int id = getId();
        storage.put(id, new Meal(id, meal));
    }

    @Override
    public void deleteMeal(int id) {
        storage.remove(id);
    }

    @Override
    public void updateMeal(Meal meal) {
        storage.computeIfPresent(meal.getId(), (k,v) -> meal);
    }

    @Override
    public List<Meal> getAllMeals() {
        return storage.values().stream().collect(Collectors.toList());
//        new ArrayList<>(storage.values());
    }

    private int getId() {
        return counter.incrementAndGet();
    }
}
