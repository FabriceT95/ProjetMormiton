package models;

public class MealType {
    private int id_meal_type;
    private String name;

    public MealType(int id_meal_type, String name) {
        this.id_meal_type = id_meal_type;
        this.name = name;
    }

    public int getId_meal_type() {
        return id_meal_type;
    }

    public void setId_meal_type(int id_meal_type) {
        this.id_meal_type = id_meal_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
