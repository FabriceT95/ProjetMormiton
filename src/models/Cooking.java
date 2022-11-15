package models;

public class Cooking {
    private int id_cooking;
    private String name;

    public Cooking(int id_cooking, String name) {
        this.id_cooking = id_cooking;
        this.name = name;
    }

    public int getId_cooking() {
        return id_cooking;
    }

    public void setId_cooking(int id_cooking) {
        this.id_cooking = id_cooking;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Cooking{" +
                "id_cooking=" + id_cooking +
                ", name='" + name + '\'' +
                '}';
    }
}

