package com.expensetracker.dao;

import java.util.List;
import java.util.Optional;

import com.expensetracker.model.Category;
import com.expensetracker.model.User;

public interface CategoryDao {
    void save(Category category);

    List<Category> findAll(User user);

    Optional<Category> findById(long id);
}