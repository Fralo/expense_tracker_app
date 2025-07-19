package com.expensetracker.dao;

import java.util.List;

import com.expensetracker.model.Category;

public interface CategoryDao {
    void save(Category category);
    List<Category> findAll();
} 