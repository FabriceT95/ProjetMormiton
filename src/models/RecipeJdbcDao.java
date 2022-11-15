package models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RecipeJdbcDao implements CrudDAO<Recipe> {
    @Override
    public List<Recipe> findAll() {
        List<Recipe> recipeList = new ArrayList<>();
        Connection connection = ConnectionManager.getConnectionInstance();
        try (Statement statement = connection.createStatement();) {
            String query = "SELECT id_recipe, isPrivate, title, " +
                    "ingredientsList, steps, servings, " +
                    "prepDuration, bakingTime, restTime, " +
                    "cost, createdAt, noteOfTheAuthor, " +
                    "d.id_difficulty, d.name as 'difficulty_name', c.id_cooking, c.name as 'cooking_name', u.id_user as 'idUser', lastName, firstName, email FROM recipe r " +
                    "INNER JOIN users u ON u.id_user = r.id_user " +
                    "INNER JOIN difficulty d ON d.id_difficulty = r.id_difficulty " +
                    "INNER JOIN cooking c ON c.id_cooking = r.id_cooking";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()){
                Recipe recipe = new Recipe(rs.getLong("id_recipe"),
                        rs.getBoolean("isPrivate"),
                        rs.getString("title"),
                        rs.getString("ingredientsList"),
                        rs.getString("steps"),
                        rs.getInt("servings"),
                        rs.getInt("prepDuration"),
                        rs.getInt("bakingTime"),
                        rs.getInt("restTime"),
                        rs.getFloat("cost"),
                        rs.getDate("createdAt").toLocalDate(),
                        rs.getString("noteOfTheAuthor"),
                        new Difficulty(rs.getInt("id_difficulty"),rs.getString("difficulty_name")),
                        new Cooking(rs.getInt("id_cooking"), rs.getString("cooking_name")),
                        new User(rs.getInt("idUser"), rs.getString("lastName"), rs.getString("firstName"), rs.getString("email"))
                );
                recipeList.add(recipe);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return recipeList;
    }

    @Override
    public Optional<Recipe> findById(Long id) {
        String query = "SELECT id_recipe, isPrivate, title, " +
                "ingredientsList, steps, servings, " +
                "prepDuration, bakingTime, restTime, " +
                "cost, createdAt, noteOfTheAuthor, " +
                "d.id_difficulty, d.name as 'difficulty_name', c.id_cooking, c.name as 'cooking_name', u.id_user as 'idUser', lastName, firstName, email FROM recipe r " +
                "INNER JOIN users u ON u.id_user = r.id_user " +
                "INNER JOIN difficulty d ON d.id_difficulty = r.id_difficulty " +
                "INNER JOIN cooking c ON c.id_cooking = r.id_cooking WHERE id_recipe = ?";
        Connection connection = ConnectionManager.getConnectionInstance();
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()) {
                return Optional.of(new Recipe(rs.getLong("id_recipe"),
                        rs.getBoolean("isPrivate"),
                        rs.getString("title"),
                        rs.getString("ingredientsList"),
                        rs.getString("steps"),
                        rs.getInt("servings"),
                        rs.getInt("prepDuration"),
                        rs.getInt("bakingTime"),
                        rs.getInt("restTime"),
                        rs.getFloat("cost"),
                        rs.getDate("createdAt").toLocalDate(),
                        rs.getString("noteOfTheAuthor"),
                        new Difficulty(rs.getInt("id_difficulty"),rs.getString("difficulty_name")),
                        new Cooking(rs.getInt("id_cooking"), rs.getString("cooking_name")),
                        new User(rs.getInt("idUser"), rs.getString("lastName"), rs.getString("firstName"), rs.getString("email"))
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Recipe> findByName(String s) {
        List<Recipe> recipeList = new ArrayList<>();
        Connection connection = ConnectionManager.getConnectionInstance();
        try (Statement statement = connection.createStatement();) {
            String query = "SELECT id_recipe, isPrivate, title, " +
                    "ingredientsList, steps, servings, " +
                    "prepDuration, bakingTime, restTime, " +
                    "cost, createdAt, noteOfTheAuthor, " +
                    "d.id_difficulty, d.name as 'difficulty_name', c.id_cooking, c.name as 'cooking_name', u.id_user as 'idUser', lastName, firstName, email FROM recipe r " +
                    "INNER JOIN users u ON u.id_user = r.id_user " +
                    "INNER JOIN difficulty d ON d.id_difficulty = r.id_difficulty " +
                    "INNER JOIN cooking c ON c.id_cooking = r.id_cooking WHERE title LIKE '%?%'";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()){
                Recipe recipe = new Recipe(rs.getLong("id_recipe"),
                        rs.getBoolean("isPrivate"),
                        rs.getString("title"),
                        rs.getString("ingredientsList"),
                        rs.getString("steps"),
                        rs.getInt("servings"),
                        rs.getInt("prepDuration"),
                        rs.getInt("bakingTime"),
                        rs.getInt("restTime"),
                        rs.getFloat("cost"),
                        rs.getDate("createdAt").toLocalDate(),
                        rs.getString("noteOfTheAuthor"),
                        new Difficulty(rs.getInt("id_difficulty"),rs.getString("difficulty_name")),
                        new Cooking(rs.getInt("id_cooking"), rs.getString("cooking_name")),
                        new User(rs.getInt("idUser"), rs.getString("lastName"), rs.getString("firstName"), rs.getString("email"))
                );
                recipeList.add(recipe);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return recipeList;
    }

    @Override
    public List<Recipe> findByMealType(MealType mealType) {
        List<Recipe> recipeList = new ArrayList<>();
        Connection connection = ConnectionManager.getConnectionInstance();
        String query = "SELECT r.id_recipe as 'idRecipe', isPrivate, title, \n" +
                "                    ingredientsList, steps, servings, \n" +
                "prepDuration, bakingTime, restTime, \n" +
                "cost, createdAt, noteOfTheAuthor, \n" +
                "d.id_difficulty, d.name as 'difficulty_name', c.id_cooking, c.name as 'cooking_name', u.id_user as 'idUser', lastName, firstName, email, mt.name as 'Meal Type Name' FROM recipe r \n" +
                "                    INNER JOIN users u ON u.id_user = r.id_user \n" +
                "                    INNER JOIN difficulty d ON d.id_difficulty = r.id_difficulty \n" +
                "                    INNER JOIN hasMealType hmt ON hmt.id_recipe = r.id_recipe\n" +
                "                    INNER JOIN mealType mt ON mt.id_meal_type = hmt.id_meal_type\n" +
                "\t\t\t\tINNER JOIN cooking c ON c.id_cooking = r.id_cooking WHERE mt.id_meal_type = ?";
        try (PreparedStatement statement = connection.prepareStatement(query);) {
            statement.setLong(1, mealType.getId_meal_type());
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()){
                Recipe recipe = new Recipe(rs.getLong("id_recipe"),
                        rs.getBoolean("isPrivate"),
                        rs.getString("title"),
                        rs.getString("ingredientsList"),
                        rs.getString("steps"),
                        rs.getInt("servings"),
                        rs.getInt("prepDuration"),
                        rs.getInt("bakingTime"),
                        rs.getInt("restTime"),
                        rs.getFloat("cost"),
                        rs.getDate("createdAt").toLocalDate(),
                        rs.getString("noteOfTheAuthor"),
                        new Difficulty(rs.getInt("id_difficulty"),rs.getString("difficulty_name")),
                        new Cooking(rs.getInt("id_cooking"), rs.getString("cooking_name")),
                        new User(rs.getInt("idUser"), rs.getString("lastName"), rs.getString("firstName"), rs.getString("email"))
                );
                recipeList.add(recipe);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return recipeList;
    }




    @Override
    public List<Recipe> findByKeyword(String s) {
        String lowerCaseS = s.toLowerCase();
        List<Recipe> recipeList = new ArrayList<>();
        Connection connection = ConnectionManager.getConnectionInstance();
        String query = "SELECT r.id_recipe as 'idRecipe', isPrivate, title, \n" +
                "                    ingredientsList, steps, servings, \n" +
                "                    prepDuration, bakingTime, restTime, \n" +
                "                    cost, createdAt, noteOfTheAuthor, d.id_difficulty, d.name as 'difficulty_name', c.id_cooking, c.name as 'cooking_name', u.id_user as 'idUser', lastName, firstName\n" +
                "\t\t\t\t\tFROM recipe r \n" +
                "                    INNER JOIN users u ON u.id_user = r.id_user \n" +
                "                    INNER JOIN difficulty d ON d.id_difficulty = r.id_difficulty \n" +
                "\t\t\t\t\tINNER JOIN cooking c ON c.id_cooking = r.id_cooking WHERE (lower(title) LIKE '%choco%' OR lower(ingredientsList) LIKE '%?%' OR  lower(steps) LIKE '%?%' OR lower(noteOfTheAuthor) LIKE '%?%');";

        try (PreparedStatement statement = connection.prepareStatement(query);) {
            statement.setString(1,lowerCaseS);
            statement.setString(2,lowerCaseS);
            statement.setString(3,lowerCaseS);
            statement.setString(4,lowerCaseS);
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()){
                Recipe recipe = new Recipe(rs.getLong("idRecipe"),
                        rs.getBoolean("isPrivate"),
                        rs.getString("title"),
                        rs.getString("ingredientsList"),
                        rs.getString("steps"),
                        rs.getInt("servings"),
                        rs.getInt("prepDuration"),
                        rs.getInt("bakingTime"),
                        rs.getInt("restTime"),
                        rs.getFloat("cost"),
                        rs.getDate("createdAt").toLocalDate(),
                        rs.getString("noteOfTheAuthor"),
                        new Difficulty(rs.getInt("id_difficulty"),rs.getString("difficulty_name")),
                        new Cooking(rs.getInt("id_cooking"), rs.getString("cooking_name")),
                        new User(rs.getInt("idUser"), rs.getString("lastName"), rs.getString("firstName"), rs.getString("email"))
                );
                recipeList.add(recipe);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return recipeList;
    }

    @Override
    public Recipe findByRandom() {
        String query = "SELECT r.id_recipe as 'idRecipe', isPrivate, title," +
                "                ingredientsList, steps, servings," +
                "                prepDuration, bakingTime, restTime," +
                "                cost, createdAt, noteOfTheAuthor," +
                "                d.id_difficulty, d.name as 'difficulty_name'," +
                "                c.id_cooking, c.name as 'cooking_name', u.id_user as 'idUser', lastName, firstName, email FROM recipe r " +
                "                INNER JOIN users u ON u.id_user = r.id_user " +
                "                INNER JOIN difficulty d ON d.id_difficulty = r.id_difficulty " +
                "                INNER JOIN cooking c ON c.id_cooking = r.id_cooking " +
                "                INNER JOIN cooks cks ON cks.id_user = u.id_user AND cks.id_recipe = r.id_recipe " +
                "                WHERE datediff(NOW(), cks.cookingDate) > 6 ORDER BY RAND() LIMIT 1;";
        Connection connection = ConnectionManager.getConnectionInstance();
        try(Statement statement = connection.createStatement();) {
            ResultSet rs = statement.executeQuery(query);
            if(rs.next()) {
                return new Recipe(rs.getLong("idRecipe"),
                        rs.getBoolean("isPrivate"),
                        rs.getString("title"),
                        rs.getString("ingredientsList"),
                        rs.getString("steps"),
                        rs.getInt("servings"),
                        rs.getInt("prepDuration"),
                        rs.getInt("bakingTime"),
                        rs.getInt("restTime"),
                        rs.getFloat("cost"),
                        rs.getDate("createdAt").toLocalDate(),
                        rs.getString("noteOfTheAuthor"),
                        new Difficulty(rs.getInt("id_difficulty"),rs.getString("difficulty_name")),
                        new Cooking(rs.getInt("id_cooking"), rs.getString("cooking_name")),
                        new User(rs.getInt("idUser"), rs.getString("lastName"), rs.getString("firstName"), rs.getString("email")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     Delete from messages where messageid = '1';
     Delete from usersmessages where messageid = '1'
     DELETE t1, t2 FROM t1 INNER JOIN t2 INNER JOIN t3 WHERE t1.id=t2.id AND t2.id=t3.id;
     */

    @Override
    public boolean delete(Long id) throws SQLException {

        Connection connection = ConnectionManager.getConnectionInstance();

        String query = " DELETE FROM recipe WHERE id_recipe = ?;";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            int numberRow = preparedStatement.executeUpdate();
            connection.commit();
            return numberRow > 0;
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
            System.out.println("Something went wrong when deleting a recipe !");
        }

        return false;
    }

    // NOT DONE
    @Override
    public Recipe update(Recipe element) {
        long id = element.getId_recipe();
        String name = book.getName();
        String query = "UPDATE book SET name=? WHERE id=?";
        Connection connection = java_jdbc.demo.models.ConnectionManager.getConnectionInstance();
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Recipe create(Recipe element) {
        return null;
    }
}
