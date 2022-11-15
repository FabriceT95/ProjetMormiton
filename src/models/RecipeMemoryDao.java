package models;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class RecipeMemoryDao {
    public RecipeMemoryDao(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public List<Recipe> getRecipes() {
        return this.recipes;
    }

    public  void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    private List<Recipe> recipes;

    /*@Override
    public List<Recipe> findAll() {
        return this.recipes;
    }

    @Override
    public Optional<Recipe> findById(Long id) {
        return this.recipes.stream().filter(r -> r.getId_recipe() == id).findAny();
    }

    @Override
    public List<Recipe> findByName(String s) {
        return this.recipes.stream().filter(r -> r.getTitle().toLowerCase().contains(s.toLowerCase())).toList();
    }

    @Override
    public List<Recipe> findByMealType(MealType mealType) {
        return null;
    }

    @Override
    public List<Recipe> findByKeyword(String s) {
        return null;
    }

    @Override
    public Recipe findByRandom() {
        return null;
    }

    @Override
    public boolean delete(Long id) throws SQLException {
        return false;
    }

    @Override
    public Recipe update(Recipe element) throws SQLException {
        return null;
    }

    @Override
    public boolean setMomentsOfTheDay(Recipe element, List<Integer> momentsId) throws SQLException {
        return false;
    }

    @Override
    public boolean setMealTypes(Recipe element, List<Integer> mealTypesId) throws SQLException {
        return false;
    }

    @Override
    public Recipe create(Recipe element) throws SQLException {
        return null;
    }*/
}
