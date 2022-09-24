package by.kharchenko.springcafe.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

import static by.kharchenko.springcafe.controller.DbColumn.*;


@Entity
@Table(name = INGREDIENTS)
public class Ingredient extends AbstractEntity implements Serializable, Comparable<Ingredient> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID_INGREDIENT)
    private BigInteger idIngredient;

    @Column(name = NAME)
    private String name;

    @Column(name = SHELF_LIFE)
    private LocalDate shelfLife;

    transient private double grams;

    @ManyToMany
    @JoinTable(name = PRODUCTS_INGREDIENTS, joinColumns = @JoinColumn(name = ID_INGREDIENT), inverseJoinColumns = @JoinColumn(name = ID_PRODUCT))
    private Set<Product> products;


    public BigInteger getIdIngredient() {
        return idIngredient;
    }

    public void setIdIngredient(BigInteger idIngredient) {
        this.idIngredient = idIngredient;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getShelfLife() {
        return shelfLife;
    }

    public void setShelfLife(LocalDate shelfLife) {
        this.shelfLife = shelfLife;
    }

    public double getGrams() {
        return grams;
    }

    public void setGrams(double grams) {
        this.grams = grams;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    @Override
    public int compareTo(Ingredient o) {
        return this.shelfLife.compareTo(o.shelfLife);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return Double.compare(that.grams, grams) == 0 && Objects.equals(idIngredient, that.idIngredient) && Objects.equals(name, that.name) && Objects.equals(shelfLife, that.shelfLife);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idIngredient, name, shelfLife, grams);
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "idIngredient=" + idIngredient +
                ", name='" + name + '\'' +
                ", shelfLife=" + shelfLife +
                ", grams=" + grams +
                '}';
    }


}
