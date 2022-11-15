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
    public List<Recipe> findByMealType(String s) {
        return null;
    }

    @Override
    public List<Recipe> findByKeyword(String s) {
        return null;
    }

    @Override
    public Recipe findByRandom() {
        String query = "SELECT id_recipe, isPrivate, title, " +
                "ingredientsList, steps, servings, " +
                "prepDuration, bakingTime, restTime, " +
                "cost, createdAt, noteOfTheAuthor, " +
                "d.id_difficulty, d.name as 'difficulty_name', c.id_cooking, c.name as 'cooking_name', u.id_user as 'idUser', lastName, firstName, email FROM recipe r " +
                "INNER JOIN users u ON u.id_user = r.id_user " +
                "INNER JOIN difficulty d ON d.id_difficulty = r.id_difficulty " +
                "INNER JOIN cooking c ON c.id_cooking = r.id_cooking ORDER BY RAND() LIMIT 1";
        Connection connection = ConnectionManager.getConnectionInstance();
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()) {
                return new Recipe(rs.getLong("id_recipe"),
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
       /* String query = "DELETE iat, hmt, co, cmt, r " +
                "FROM recipe r " +
                "INNER JOIN isAppropriatedTo iat ON iat.id_recipe = r.id_recipe " +
                "INNER JOIN hasMealType hmt ON hmt.id_recipe = r.id_recipe " +
                "INNER JOIN cooks co ON co.id_recipe = r.id_recipe " +
                "INNER JOIN comment cmt ON cmt.id_recipe = r.id_recipe " +
                "WHERE r.id_recipe = ?;";*/

       /* String query = "DELETE FROM isAppropriatedTo WHERE id_recipe = ?; " +
                "DELETE FROM hasMealType WHERE id_recipe = ?; " +
                "DELETE FROM cooks WHERE id_recipe = ?; " +
                "DELETE FROM comment WHERE id_recipe = ?; " +
                "DELETE FROM recipe WHERE id_recipe = ?";*/
       // String query = "DELETE FROM  WHERE id_recipe = ?";


        Connection connection = ConnectionManager.getConnectionInstance();

        // Faire le delete en cascade avec un procédure;
       /* try(PreparedStatement preparedStatement = connection.prepareCall("CALL delete_recette_procedure(?)")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
            System.out.println("Something went wrong when deleting a recipe !");
            return false;
        }*/


        String query1 = "DELETE FROM isAppropriatedTo WHERE id_recipe = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query1)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
            System.out.println("Something went wrong when deleting a recipe !");
            return false;
        }

        String query2 = "DELETE FROM hasMealType WHERE id_recipe = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query2)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
            System.out.println("Something went wrong when deleting a recipe !");
            return false;
        }

        String query3 = "DELETE FROM cooks WHERE id_recipe = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query3)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
            System.out.println("Something went wrong when deleting a recipe !");
            return false;
        }

        String query4 = "DELETE FROM comment WHERE id_recipe = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query4)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
            System.out.println("Something went wrong when deleting a recipe !");
            return false;
        }

        String query5 = "DELETE FROM recipe WHERE id_recipe = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query5)) {
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
