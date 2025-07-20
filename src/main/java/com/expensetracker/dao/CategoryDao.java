package com.expensetracker.dao;

import java.util.List;
import java.util.Optional;

import com.expensetracker.model.Category;

public interface CategoryDao {
    void save(Category category);

    List<Category> findAll();

    Optional<Category> findById(long id);
}