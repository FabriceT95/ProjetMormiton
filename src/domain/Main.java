package domain;

import models.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        CrudDAO<Recipe> recipeDao = DaoFactory.getRecipeDao();

       afficher("(Les saisies sur la console s'effectuent en se positionnant sur la ligne suivante)");
        int option = 0;
        int filtre = 0;
        Recipe recipe = null;
        boolean looping = true;


        while(looping){
            afficher("Effectuez votre choix (saisissez uniquement le nombre, puis cliquez sur la touche Entrée) :");
            afficher("1 - Consulter toutes les recettes (dans l'ordre chronologique de leur création");
            afficher("2 - Consulter les recettes à l'aide de filtres (mots clés, plat, moment de la journée, type de cuisson");
            afficher("3 - Créer une recette");
            afficher("4 - Obtenir une suggestion de recette que vous n'avez pas préparée ces derniers jours");
            afficher("5 - Obtenir les détails sur une recette à l'aide de son identifiant");
            afficher("6 - Supprimer une recette");
            afficher("7 - Arrêter le programme !");
            option = recupererEntier("");
            switch (option) {
                case 1 -> {
                    List<Recipe> recipes = recipeDao.findAll();
                    if (recipes == null || recipes.size() == 0)
                        afficher("Vous n'avez encore aucune recette");
                    else {
                        afficher("Voici les recettes que vous avez enregistrées : ");
                        for (Recipe tempRecipe : recipes) {
                            afficher("\t" + tempRecipe.getTitle() + " (id : " + tempRecipe.getId_recipe() + ")");
                        }
                    }
                }
                case 2 -> {
                    afficher("1 - Par mot clé");
                    afficher("2 - Par plat");
                   /* afficher("3 - Par moment de la journée");
                    afficher("4 - Par type de cuisson");*/
                    filtre = recupererEntier("3 - Retour au menu principal");
                    List<Recipe> filteredRecipes = new ArrayList<>();
                    switch (filtre) {
                        case 1 -> {
                            String keyWord = recupererString("Saisissez le mot-clé");
                            filteredRecipes = recipeDao.findByKeyword(keyWord);
                            afficher("Toutes les recettes retournées avec le mot-clé " + keyWord + " :");
                            filteredRecipes.forEach(System.out::println);
                        }
                        case 2 -> {
                            int mealTypeId = 0;
                            List<MealType> mealTypes = recipeDao.findAllMealTypes();
                            for (int i = 0; i < mealTypes.size(); i++) {
                                afficher((i + 1) + " - " + mealTypes.get(i).getName());
                            }
                            mealTypeId = recupererEntier("");
                            filteredRecipes = recipeDao.findByMealType(new MealType(mealTypeId, ""));
                            afficher("Toutes les recettes retournées dont le type de plat est " + mealTypeId + " :");
                            filteredRecipes.forEach(System.out::println);
                        }
                        default -> {
                        }
                    }
                }
                case 3 -> {
                    String name = recupererString("Saisissez le nom de la recette");
                    String steps = recupererString("Décrivez les étapes à réaliser");
                    String ingredients = recupererString("Saisissez les ingrédients");
                    int servings = recupererEntier("Saisissez le nombre de parts");
                    int prepDuration = recupererEntier("Saisissez la durée de préparation en minutes");
                    int bakingTime = recupererEntier("Saisissez la durée de cuisson en minutes");
                    int restTime = recupererEntier("Saisissez le temps de repos en minutes");
                    float cost = (float) recupererEntier("Saisissez le coût de la recette");
                    String notes = recupererString("Ajoutez un message concernant votre recette");
                    afficher("Evaluez la difficulté de réalisation de la recette");
                    afficher("1 - Très facile");
                    afficher("2 - Facile");
                    afficher("3 - Moyen");
                    int difficulty = recupererEntier("4 - Difficile");
                    afficher("Mode de cuisson de la recette");
                    afficher("1 - Pas de cuisson");
                    afficher("2 - Four");
                    afficher("3 - Vapeur");
                    afficher("4 - Poële");
                    afficher("5 - Cocotte minute");
                    int cooking = recupererEntier("6 - Micro-ondes");
                    recipe = recipeDao.create(new Recipe(0L, false, name, ingredients, steps, servings, prepDuration, bakingTime, restTime, cost, LocalDate.now(), notes, new Difficulty(difficulty, ""), new Cooking(cooking, ""), new User(1, "", "", "")));
// -> à continuer
                    System.out.println(recipe.toString());
                    afficher("Type de repas :");
                    afficher("1 - Petit déjeuner");
                    afficher("2 - Entrée");
                    afficher("3 - Plat");
                    afficher("4 - Dessert");
                    afficher("5 - Collation");
                    afficher("6 - Goûter");
                    afficher("7 - Prochaine étape");
                    List<Long> mealTypes = new ArrayList<>();
                    long mealType = 0;
                    do {
                        mealType =  recupererEntier("Ma recette est fait pour le... (7 pour arrêter)");
                        if(mealType < 7) {
                            mealTypes.add(mealType);
                        }
                    } while (mealType < 7);
                    recipeDao.setMealTypes(recipe, mealTypes);


                    System.out.println(recipe.toString());

                    afficher("Moment de la journée :");
                    afficher("1 - Matin");
                    afficher("2 - Midi");
                    afficher("3 - Après-midi");
                    afficher("4 - Soir");
                    afficher("5 - Prochaine étape");
                    List<Integer> momentsId = new ArrayList<>();
                    int momentId = 0;
                    do {
                        momentId = recupererEntier("Le résultat de ma recette se mange le ... (5 pour arrêter)");
                        if(momentId < 5) {
                            momentsId.add(momentId);
                        }
                    } while (momentId != 5);
                    recipeDao.setMomentsOfTheDay(recipe, momentsId);
                    System.out.println("Toutes les composantes de la recette ont été créé avec succès !");
                }
                case 4 -> {
                    recipe = recipeDao.findByRandom();
                    afficher("Voici une recette aléatoire que vous n'avez pas cuisiné depuis 7 jours : ");
                    afficher(recipe.toString());
                }
                case 5 -> {
                    long recipeId = recupererEntier("Chercher une recette par son ID : ");
                    Optional<Recipe> optionalRecipe = recipeDao.findById(recipeId);
                    System.out.println(optionalRecipe.get());
                }
                case 6 -> {

                    List<Recipe> recipes = recipeDao.findAll();
                    if (recipes == null || recipes.size() == 0)
                        afficher("Vous n'avez encore aucune recette");
                    else {
                        afficher("Voici les recettes que vous avez enregistrées : ");
                        for (Recipe tempRecipe : recipes) {
                            afficher("\t" + tempRecipe.getTitle() + " (id : " + tempRecipe.getId_recipe() + ")");
                        }
                    }

                    long recipeId = recupererEntier("Sélectionnez l'ID de la recette à supprimer (Pour annuler, sélectionnez 0");
                    if(recipeId > 0) {
                        recipeDao.delete(recipeId);
                    }

                }
                case 7 -> looping = false;
                default -> afficher("Saisie erronnée, recommencez svp");
            }
        }

    }
    public static void afficher(String message){
        System.out.println(message);
    }
    public static int recupererEntier(String message){
        Scanner sc = new Scanner(System.in);
        if(message.length() > 0) afficher(message);
        return sc.nextInt();
    }
    public static String recupererString(String message){
        Scanner sc = new Scanner(System.in);
        if(message.length() > 0) afficher(message);
        return sc.nextLine();
    }



}
