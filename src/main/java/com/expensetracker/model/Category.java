package com.expensetracker.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a financial category (e.g., "Food", "Transport"). A category can
 * contain sub-categories, realising the Composite pattern so that an entire
 * hierarchy can be treated uniformly. -- non si fa realmente cosi, da rivedere
 */
public class Category {

    private long id;
    private long userId;
    private final String name;
    private final List<Category> subCategories = new ArrayList<>();

    public Category(long userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public Category(long id, long userId, String name) {
        this.userId = userId;
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void addSubCategory(Category category) {
        subCategories.add(category);
    }

    public void removeSubCategory(Category category) {
        subCategories.remove(category);
    }

    public List<Category> getSubCategories() {
        return Collections.unmodifiableList(subCategories);
    }

    @Override
    public String toString() {
        return name;
    }
}