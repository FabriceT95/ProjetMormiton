package domain;

import java_jdbc.TpRecettes.models.CrudDAO;
import java_jdbc.TpRecettes.models.DaoFactory;
import java_jdbc.TpRecettes.models.Recipe;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        CrudDAO<Recipe> recipeDao = DaoFactory.getRecipeDao();
       // recipeDao.findAll().forEach(System.out::println);
       /* Optional<Recipe> recipe = recipeDao.findById(1L);
        System.out.println(recipe);*/
       boolean test = recipeDao.delete(1L);
       System.out.println(test);
    }
}
