CREATE TABLE users(
	   id_user INT AUTO_INCREMENT,
	   lastName VARCHAR(50) ,
	   firstName VARCHAR(50) ,
	   email VARCHAR(50) ,
	   pwd VARCHAR(50) ,
	   PRIMARY KEY(id_user)
	);

	CREATE TABLE momentOfTheDay(
	   id_moment_of_the_day INT AUTO_INCREMENT,
	   name VARCHAR(50) ,
	   PRIMARY KEY(id_moment_of_the_day)
	);

	CREATE TABLE mealType(
	   id_meal_type INT AUTO_INCREMENT,
	   name VARCHAR(50) ,
	   PRIMARY KEY(id_meal_type)
	);

	CREATE TABLE cooking(
	   id_cooking INT AUTO_INCREMENT,
	   name VARCHAR(50) ,
	   PRIMARY KEY(id_cooking)
	);

	CREATE TABLE difficulty(
	   id_difficulty INT AUTO_INCREMENT,
	   name VARCHAR(50) ,
	   PRIMARY KEY(id_difficulty)
	);

	CREATE TABLE recipe(
	   id_recipe INT AUTO_INCREMENT,
	   isPrivate BOOLEAN,
	   title VARCHAR(50) ,
	   ingredientsList VARCHAR(250) ,
	   steps VARCHAR(1000) ,
	   servings INT,
	   prepDuration INT,
	   bakingTime INT,
	   restTime INT,
	   cost DECIMAL(15,2)  ,
	   createdAt DATE,
	   noteOfTheAuthor VARCHAR(250) ,
	   id_difficulty INT NOT NULL,
	   id_cooking INT NOT NULL,
	   id_user INT NOT NULL,
	   PRIMARY KEY(id_recipe),
	   FOREIGN KEY(id_difficulty) REFERENCES difficulty(id_difficulty),
	   FOREIGN KEY(id_cooking) REFERENCES cooking(id_cooking),
	   FOREIGN KEY(id_user) REFERENCES users(id_user)
	);

	CREATE TABLE comment(
	   id_comment INT AUTO_INCREMENT,
	   text VARCHAR(250) ,
	   mark INT,
	   id_user INT NOT NULL,
	   id_recipe INT NOT NULL,
	   PRIMARY KEY(id_comment),
	   FOREIGN KEY(id_user) REFERENCES users(id_user),
	   FOREIGN KEY(id_recipe) REFERENCES recipe(id_recipe) ON DELETE CASCADE
	);

	CREATE TABLE isAppropriatedTo(
	   id_recipe INT,
	   id_moment_of_the_day INT,
	   PRIMARY KEY(id_recipe, id_moment_of_the_day),
	   FOREIGN KEY(id_recipe) REFERENCES recipe(id_recipe) ON DELETE CASCADE ON UPDATE CASCADE,
	   FOREIGN KEY(id_moment_of_the_day) REFERENCES momentOfTheDay(id_moment_of_the_day)
	);

	CREATE TABLE hasMealType(
	   id_recipe INT,
	   id_meal_type INT,
	   PRIMARY KEY(id_recipe, id_meal_type),
	   FOREIGN KEY(id_recipe) REFERENCES recipe(id_recipe) ON DELETE CASCADE ON UPDATE CASCADE,
	   FOREIGN KEY(id_meal_type) REFERENCES mealType(id_meal_type)
	);

	CREATE TABLE cooks(
	   id_user INT,
	   id_recipe INT,
	   cookingDate DATE,
	   PRIMARY KEY(id_user, id_recipe),
	   FOREIGN KEY(id_user) REFERENCES users(id_user),
	   FOREIGN KEY(id_recipe) REFERENCES recipe(id_recipe) ON DELETE CASCADE ON UPDATE CASCADE
	);

    DELETE FROM isAppropriatedTo WHERE id_recipe = id;
    DELETE FROM hasMealType WHERE id_recipe = id;
    DELETE FROM cooks WHERE id_recipe = id;
    DELETE FROM comment WHERE id_recipe = id;
    DELETE FROM recipe WHERE id_recipe = id;

    INSERT INTO difficulty(name) VALUES("Très Facile"),("Facile"),("Moyen"),("Difficile");
    INSERT INTO mealType(name) VALUES("Petit déjeuner"),("Entrée"),("Plat"),("Dessert"), ("Collation"), ("Goûter");
    INSERT INTO cooking(name) VALUES("Pas de cuisson"),("Four"),("Vapeur"),("Poêle"), ("Cocotte minute"), ("Micro-ondes");
    INSERT INTO momentoftheday(name) VALUES("Matin"),("Midi"),("Après-midi"),("Soir");

    # CREATE A USER
    # INSERT INTO users(lastName, firstName, email, pwd) VALUES("default", "default", "test.test@gmail.com", "azerty123");

    # CREATE A RECIPE
    #INSERT INTO recipe(isPrivate, title, ingredientsList, steps, servings, prepDuration, bakingTime, restTime, cost, createdAt, noteOfTheAuthor, id_difficulty, id_cooking, id_user)
    #VALUES(false, "Fondant au chocolat", "Chocolat(250g)_Farine(250g)_Oeufs(3)_Beurre(1kg)_SucreVanillé(100g)", "Etape1:Tout mélanger_Etape2:Cuire_Etape3:Regalezvous", 4, 10,15,0,5,NOW(),"Gateau délicieux, top 1", 1,2,1);
    #INSERT INTO isAppropriatedTo (id_recipe, id_moment_of_the_day) VALUES (2,1), (2,2), (2,3), (2,4);
    #INSERT INTO hasMealType (id_recipe, id_meal_type) VALUES (2,4),(2,6);

    # CREATE A DATE OF COOKING
    #INSERT INTO cooks(id_user, id_recipe, cookingDate) VALUES(1, 2, "2022-11-1");

    # ADD A COMMENT TO A RECIPE
    # INSERT INTO Comment(text, mark, id_user, id_recipe) VALUES ("VRAIMENT DEGUEULASSSEEEEEEEEEEEEEEEEEE", 0, 1,1);

    # GET RECIPE
    # SELECT * FROM recipe r INNER JOIN users u ON u.id_user = r.id_user INNER JOIN difficulty d ON d.id_difficulty = r.id_difficulty INNER JOIN cooking c ON c.id_cooking = r.id_cooking WHERE title LIKE "%choco%";

    # GET ALL RECIPES
    # SELECT * FROM recipe r INNER JOIN users u ON u.id_user = r.id_user INNER JOIN difficulty d ON d.id_difficulty = r.id_difficulty INNER JOIN cooking c ON c.id_cooking = r.id_cooking;

    # GET COMMENTS FROM A RECIPE
    # SELECT * FROM comment WHERE id_recipe = 1;

    # RANDOM RECIPE AND LAST TIME COOCKED IS SUPERIOR THAN 6 DAYS
    /*SELECT r.id_recipe, isPrivate, title,
                    ingredientsList, steps, servings,
                    prepDuration, bakingTime, restTime,
                    cost, createdAt, noteOfTheAuthor,
                    d.id_difficulty, d.name as 'difficulty_name',
                    c.id_cooking, c.name as 'cooking_name', u.id_user as 'idUser', lastName, firstName, email, cks.cookingDate as "Cooking Date" FROM recipe r
                    INNER JOIN users u ON u.id_user = r.id_user
                    INNER JOIN difficulty d ON d.id_difficulty = r.id_difficulty
                    INNER JOIN cooking c ON c.id_cooking = r.id_cooking
                    INNER JOIN cooks cks ON cks.id_user = u.id_user AND cks.id_recipe = r.id_recipe
                    WHERE datediff(NOW(), cks.cookingDate) > 6 ORDER BY RAND() LIMIT 1;*/

    # FIND BY MEAL TYPE
    SELECT r.id_recipe as 'idRecipe', isPrivate, title,
                        ingredientsList, steps, servings,
                        prepDuration, bakingTime, restTime,
                        cost, createdAt, noteOfTheAuthor,
    					d.id_difficulty, d.name as 'difficulty_name', c.id_cooking, c.name as 'cooking_name', u.id_user as 'idUser', lastName, firstName, email, mt.name as 'Meal Type Name' FROM recipe r
                        INNER JOIN users u ON u.id_user = r.id_user
                        INNER JOIN difficulty d ON d.id_difficulty = r.id_difficulty
                        INNER JOIN hasMealType hmt ON hmt.id_recipe = r.id_recipe
                        INNER JOIN mealType mt ON mt.id_meal_type = hmt.id_meal_type
    				INNER JOIN cooking c ON c.id_cooking = r.id_cooking WHERE mt.id_meal_type = 6;


    # FIND BY KEYWORDS : FIND IN AUTHORS NOTE, DESCRIPTIONS, LISTS INGREDIENTS AND STEPS, TITLE
    /*SELECT r.id_recipe as 'idRecipe', isPrivate, title,
                        ingredientsList, steps, servings,
                        prepDuration, bakingTime, restTime,
                        cost, createdAt, noteOfTheAuthor, d.id_difficulty, d.name as 'difficulty_name', c.id_cooking, c.name as 'cooking_name', u.id_user as 'idUser', lastName, firstName
    					FROM recipe r
                        INNER JOIN users u ON u.id_user = r.id_user
                        INNER JOIN difficulty d ON d.id_difficulty = r.id_difficulty
    					INNER JOIN cooking c ON c.id_cooking = r.id_cooking WHERE (lower(title) LIKE '%choco%' OR lower(ingredientsList) LIKE '%?%' OR  lower(steps) LIKE '%?%' OR lower(noteOfTheAuthor) LIKE '%?%');
    */

    "UPDATE A RECIPE"
    /*"UPDATE recipe SET title=?, " +
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
    */


    /*
    DELETE isAppropriatedTo, hasMealType, cooks, comment, recipe FROM recipe , isAppropriatedTo, hasMealType , cooks , comment
    INNER JOIN isAppropriatedTo  ON isAppropriatedTo.id_recipe = recipe.id_recipe
     INNER JOIN hasMealType  ON hasMealType.id_recipe = recipe.id_recipe
     INNER JOIN cooks  ON cooks.id_recipe = recipe.id_recipe
     INNER JOIN comment  ON comment.id_recipe = recipe.id_recipe
     WHERE r.id_recipe = 1;
     */
     # DELETE FROM recipe WHERE id_recipe = 1;

     /*DELETE isAppropriatedTo, hasMealType, cooks, comment, recipe FROM isAppropriatedTo, hasMealType, cooks, comment, recipe
     WHERE isAppropriatedTo.id_recipe = recipe.id_recipe AND hasMealType.id_recipe = recipe.id_recipe AND cooks.id_recipe = recipe.id_recipe AND comment.id_recipe = recipe.id_recipe AND recipe.id_recipe = 1;
    */
    # SELECT * FROM recipe r INNER JOIN users u ON u.id_user = r.id_user INNER JOIN difficulty d ON d.id_difficulty = r.id_difficulty INNER JOIN cooking c ON c.id_cooking = r.id_cooking;


    # SELECT * FROM recipe;