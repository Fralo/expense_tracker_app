package com.expensetracker.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a financial category (e.g., "Food", "Transport"). A category can
 * contain sub-categories, realising the Composite pattern so that an entire
 * hierarchy can be treated uniformly.
 */
public class Category {

    private final String name;
    private final List<Category> subCategories = new ArrayList<>();

    public Category(String name) {
        this.name = name;
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