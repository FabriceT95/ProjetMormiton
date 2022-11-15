package models;

import java.util.List;

public class DaoFactory {
    private DaoFactory() {

    }

    public static CrudDAO<Recipe> getRecipeDao(){
        // return new BookMemoryDao();
        return new RecipeJdbcDao();
    }
    /*public static CrudDAO<Recipe> getRecipeMemoryDao(List<Recipe> recipes){
        return new RecipeMemoryDao(recipes);
    }*/
}
