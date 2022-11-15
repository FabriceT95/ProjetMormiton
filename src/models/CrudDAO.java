package models;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CrudDAO<E> {
    List<E> findAll();

    Optional<E> findById(Long id);

    List<E> findByName(String s);

    List<E> findByMealType(MealType mealType);

    List<E> findByKeyword(String s);

    E findByRandom();

    boolean delete(Long id) throws SQLException;

    E update(E element);

    E create(E element);


}
