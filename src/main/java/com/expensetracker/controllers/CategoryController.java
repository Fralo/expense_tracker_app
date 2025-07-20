package com.expensetracker.controllers;

import java.util.List;

import com.expensetracker.dao.CategoryDao;
import com.expensetracker.dao.jdbc.JdbcCategoryDao;
import com.expensetracker.model.Category;
import com.expensetracker.model.User;

public class CategoryController {
    private final CategoryDao categoryDao;

    public CategoryController() {
        this.categoryDao = new JdbcCategoryDao();
    }

    public void createCategory(String name) {
        Category category = new Category(name);
        categoryDao.save(category);
    }

    public Category getCategoryById(long id) {
        return categoryDao.findById(id).orElse(null);
    }

    public List<Category> getAllCategories(User user) {
        return categoryDao.findAll(user);
    }
}
