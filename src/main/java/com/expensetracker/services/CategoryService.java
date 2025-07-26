package com.expensetracker.services;

import java.util.List;

import com.expensetracker.dao.CategoryDao;
import com.expensetracker.dao.jdbc.JdbcCategoryDao;
import com.expensetracker.model.Category;
import com.expensetracker.model.User;

public class CategoryService {
    private final CategoryDao categoryDao;

    public CategoryService() {
        this.categoryDao = new JdbcCategoryDao();
    }

    public void createCategory(User user, String name) {
        Category category = new Category(user.getId(), name);
        categoryDao.save(category);
    }

    public Category getCategoryById(long id) {
        return categoryDao.findById(id).orElse(null);
    }

    public List<Category> getAllCategories(User user) {
        return categoryDao.findAll(user);
    }
}
