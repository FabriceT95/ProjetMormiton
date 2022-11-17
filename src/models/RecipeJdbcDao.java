package models;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class RecipeJdbcDao implements CrudDAO<Recipe>  {
    @Override
    public List<Recipe> findAll() {
        List<Recipe> recipeList = new ArrayList<>();

        try (Connection connection = ConnectionManager.getConnectionInstance();Statement statement = connection.createStatement();) {
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
                "                    prepDuration, bakingTime, restTime, \n" +
                "                    cost, createdAt, noteOfTheAuthor, \n" +
                "\t\t\t\t\td.id_difficulty, d.name as 'difficulty_name', c.id_cooking, c.name as 'cooking_name', u.id_user as 'idUser', lastName, firstName, email, mt.name as 'Meal Type Name' FROM recipe r \n" +
                "                    INNER JOIN users u ON u.id_user = r.id_user \n" +
                "                    INNER JOIN difficulty d ON d.id_difficulty = r.id_difficulty \n" +
                "                    INNER JOIN hasMealType hmt ON hmt.id_recipe = r.id_recipe\n" +
                "                    INNER JOIN mealType mt ON mt.id_meal_type = hmt.id_meal_type\n" +
                "\t\t\t\tINNER JOIN cooking c ON c.id_cooking = r.id_cooking WHERE mt.id_meal_type = ? ;";
        try (PreparedStatement statement = connection.prepareStatement(query);) {
            statement.setLong(1, mealType.getId_meal_type());
            ResultSet rs = statement.executeQuery();
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
    public List<Recipe> findByKeyword(String s) {
        String lowerCaseS = s.toLowerCase();
        List<Recipe> recipeList = new ArrayList<>();
        Connection connection = ConnectionManager.getConnectionInstance();
        String query = "SELECT r.id_recipe as 'idRecipe', isPrivate, title," +
                "ingredientsList, steps, servings," +
                "prepDuration, bakingTime, restTime," +
                "cost, createdAt, noteOfTheAuthor, d.id_difficulty, d.name as 'difficulty_name', c.id_cooking, c.name as 'cooking_name', u.id_user as 'idUser', lastName, firstName" +
                "FROM recipe r" +
                "                    INNER JOIN users u ON u.id_user = r.id_user" +
                "                    INNER JOIN difficulty d ON d.id_difficulty = r.id_difficulty " +
                "\t\t\t\t\tINNER JOIN cooking c ON c.id_cooking = r.id_cooking WHERE (lower(title) LIKE '%?%' OR lower(ingredientsList) LIKE '%?%' OR  lower(steps) LIKE '%?%' OR lower(noteOfTheAuthor) LIKE '%?%');";

        try (PreparedStatement statement = connection.prepareStatement(query);) {
            statement.setString(1,lowerCaseS);
            statement.setString(2,lowerCaseS);
            statement.setString(3,lowerCaseS);
            statement.setString(4,lowerCaseS);
            ResultSet rs = statement.executeQuery();
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

    @Override
    public boolean delete(Long id) throws SQLException {
                String query = " DELETE FROM recipe WHERE id_recipe = ?;";
        try(Connection connection = ConnectionManager.getConnectionInstance(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            int numberRow = preparedStatement.executeUpdate();
            connection.commit();
            return numberRow > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Something went wrong when deleting a recipe !");
        }

        return false;
    }

    // NOT DONE
    @Override
    public Recipe update(Recipe element) throws SQLException {
        String query = "UPDATE recipe SET title=?, " +
                "isPrivate=?, " +
                "ingredientsList=?, " +
                "steps=?, " +
                "servings=?, " +
                "prepDuration=?, " +
                "bakingTime=?, " +
                "restTime=?, " +
                "cost=?, " +
                "noteOfTheAuthor=?, " +
                "id_difficulty=?, " +
                "id_cooking=? " +
                "WHERE id=?";

        Connection connection = ConnectionManager.getConnectionInstance();
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, element.getTitle());
            preparedStatement.setBoolean(2, element.isPrivate());
            preparedStatement.setString(3, element.getIngredientsList());
            preparedStatement.setString(4, element.getSteps());
            preparedStatement.setInt(5, element.getServings());
            preparedStatement.setInt(6, element.getPreDuration());
            preparedStatement.setInt(7, element.getBakingTime());
            preparedStatement.setInt(8, element.getRestTime());
            preparedStatement.setFloat(9, element.getCost());
            preparedStatement.setString(10, element.getNoteOfTheAuthor());
            preparedStatement.setInt(11, element.getDifficulty().getId_difficulty());
            preparedStatement.setInt(12, element.getCooking().getId_cooking());
            preparedStatement.setLong(13, element.getId_recipe());
            preparedStatement.executeUpdate();
            connection.commit();
            return element;
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean setMomentsOfTheDay(Recipe element, List<Integer> momentsId) throws SQLException {
        String query1 = "DELETE FROM isAppropriatedTo WHERE id_recipe = ?";
        StringBuilder query2 = new StringBuilder("INSERT INTO isAppropriatedTo(id_recipe, id_moment_of_the_day) VALUES ");
        for(int i = 0; i<momentsId.size(); i++) {
            query2.append("(?, ").append(momentsId.get(i)).append(")");
            if(i <momentsId.size()-1) query2.append(',');
        }
        System.out.println("Id_recipe  : " + element.getId_recipe());

        try(Connection connection = ConnectionManager.getConnectionInstance();) {
            System.out.println(2);
            try(PreparedStatement preparedStatement1 = connection.prepareStatement(query1)) {
                System.out.println(3);
                preparedStatement1.setLong(1, element.getId_recipe());
                System.out.println(4);
                preparedStatement1.executeUpdate();
                System.out.println(5);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
                return false;
            }
            try(PreparedStatement preparedStatement2 = connection.prepareStatement(query2.toString())) {
                System.out.println(6);
                for(int i = 0; i < momentsId.size(); i++) {
                    System.out.println("("+element.getId_recipe()+","+momentsId.get(i)+" )");
                    preparedStatement2.setLong(i+1, element.getId_recipe());
                }
                preparedStatement2.executeUpdate();
                connection.commit();
                return true;
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            }


        }




        return false;
    }

    @Override
    public boolean setMealTypes(Recipe element, List<Long> mealTypesId) throws SQLException {
        String query1 = "DELETE FROM hasMealType WHERE id_recipe = ?";
        StringBuilder query2 = new StringBuilder("INSERT INTO hasMealType(id_recipe, id_meal_type) VALUES ");
        for(int i = 0; i<mealTypesId.size(); i++) {
            query2.append("(?, ").append(mealTypesId.get(i)).append(")");
            if(i <mealTypesId.size()-1) query2.append(',');
        }

            try (Connection connection = ConnectionManager.getConnectionInstance();) {
                try(PreparedStatement preparedStatement1 = connection.prepareStatement(query1);) {
                    preparedStatement1.setLong(1, element.getId_recipe());
                    preparedStatement1.executeUpdate();

                }

                try(
                    PreparedStatement preparedStatement2 = connection.prepareStatement(query2.toString())) {
                    for(int i = 0; i < mealTypesId.size(); i++) {
                        preparedStatement2.setLong(i+1, element.getId_recipe());
                    }
                    preparedStatement2.executeUpdate();
                    connection.commit();
                    return true;
                } catch (SQLException e) {
                    // connection.rollback();
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                // connection.rollback();
                e.printStackTrace();
                return false;
            }




        return false;
    }

    @Override
    public Recipe create(Recipe element) throws SQLException {
        String query = "INSERT INTO recipe(isPrivate, title, ingredientsList, " +
                "steps, servings, prepDuration, bakingTime, " +
                "restTime, cost, createdAt, noteOfTheAuthor, " +
                "id_difficulty, id_cooking, id_user)" +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try(Connection connection = ConnectionManager.getConnectionInstance();
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setBoolean(1, element.isPrivate());
            preparedStatement.setString(2, element.getTitle());
            preparedStatement.setString(3, element.getIngredientsList());
            preparedStatement.setString(4, element.getSteps());
            preparedStatement.setInt(5, element.getServings());
            preparedStatement.setInt(6, element.getPreDuration());
            preparedStatement.setInt(7, element.getBakingTime());
            preparedStatement.setInt(8, element.getRestTime());
            preparedStatement.setFloat(9, element.getCost());
            preparedStatement.setDate(10, java.sql.Date.valueOf(element.getCreatedAt()));
            preparedStatement.setString(11, element.getNoteOfTheAuthor());
            preparedStatement.setInt(12, element.getDifficulty().getId_difficulty());
            preparedStatement.setInt(13, element.getCooking().getId_cooking());
            preparedStatement.setInt(14, element.getUser().getId_user());
            int affectedRows = preparedStatement.executeUpdate();
            connection.commit();
            if(affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }
            try(ResultSet rs = preparedStatement.getGeneratedKeys()) {
                if(rs.next()) {
                    element.setId_recipe(rs.getLong(1));
                    return element;
                } else {
                    System.out.println("Recipe didn't get created !");
                    return null;
                }
            } catch (SQLException s) {
                s.printStackTrace();
                return null;
            }
        }catch (SQLException e) {
            System.out.println("Book didn't get created !");
            // connection.rollback();
            e.printStackTrace();
            return null;
        }
    }

    public List<MealType> findMealTypesId(Recipe r){
        List<MealType> mealTypes = new ArrayList<>();
        Connection connection = ConnectionManager.getConnectionInstance();
        String query = " SELECT * from hasMealType hmt " +
                "INNER JOIN mealType ON mt hmt.id_meal_type = mt.id_meal_type " +
                "where hmt.id_recipe = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, r.getId_recipe());
            ResultSet rs = preparedStatement.executeQuery();
            /*while(rs.next()){
                mealTypes.add(new MealType(rs.getLong("id_meal_type"), rs.getLong("id_recipe")));
            }*/
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Something went wrong when deleting a recipe !");
        }

        return null;
    }
    @Override
     public List<MealType> findAllMealTypes(){
         List<MealType> mealTypes = new ArrayList<>();
         Connection connection = ConnectionManager.getConnectionInstance();
         String query = " SELECT * from mealType";
         try(Statement statement = connection.createStatement();) {
             ResultSet rs = statement.executeQuery(query);
            while(rs.next()){
                mealTypes.add(new MealType(rs.getInt("id_meal_type"), rs.getString("name")));
            }
             return mealTypes;
         } catch (SQLException e) {
             e.printStackTrace();
             System.out.println("Something went getting all meal types !");
         }

         return null;
     }
}
