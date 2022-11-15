package models;

public class DaoFactory {
    private DaoFactory() {

    }

    public static CrudDAO<Recipe> getRecipeDao(){
        // return new BookMemoryDao();
        return new RecipeJdbcDao();
    }
}
