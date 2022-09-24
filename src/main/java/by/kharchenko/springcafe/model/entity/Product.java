package by.kharchenko.springcafe.model.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

import static by.kharchenko.springcafe.controller.DbColumn.*;

@Entity
@Table(name = PRODUCTS)
public class Product extends AbstractEntity implements Serializable, Comparable<Product> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = ID_PRODUCT)
    private BigInteger idProduct;

    @Column(name = NAME)
    private String name;

    @Column(name = VALIDITY_DATE)
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    private Date validityDate;

    @Temporal(TemporalType.DATE)
    @Column(name = REGISTRATION_TIME)
    @DateTimeFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    private Date registrationTime;

    @Column(name = PRICE)
    private BigDecimal price;

    @Column(name = PHOTO_PATH)
    private String photoPath;

    transient private String stringPhoto;

    @ManyToMany(mappedBy = PRODUCTS)
    private Set<Order> orders;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = PRODUCTS_INGREDIENTS, joinColumns = @JoinColumn(name = ID_PRODUCT), inverseJoinColumns = @JoinColumn(name = ID_INGREDIENT))
    private Set<Ingredient> ingredients;


    public BigInteger getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(BigInteger idProduct) {
        this.idProduct = idProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getStringPhoto() {
        return stringPhoto;
    }

    public void setStringPhoto(String stringPhoto) {
        this.stringPhoto = stringPhoto;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Date getValidityDate() {
        return validityDate;
    }

    public void setValidityDate(Date validityDate) {
        this.validityDate = validityDate;
    }

    public Date getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(Date registrationTime) {
        this.registrationTime = registrationTime;
    }

    @Override
    public int compareTo(Product o) {
        return this.registrationTime.compareTo(o.registrationTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(idProduct, product.idProduct) && Objects.equals(name, product.name) && Objects.equals(validityDate, product.validityDate) && Objects.equals(registrationTime, product.registrationTime) && Objects.equals(price, product.price) && Objects.equals(photoPath, product.photoPath) && Objects.equals(stringPhoto, product.stringPhoto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProduct, name, validityDate, registrationTime, price, photoPath, stringPhoto);
    }

    @Override
    public String toString() {
        return "Product{" +
                "idProduct=" + idProduct +
                ", name='" + name + '\'' +
                ", validityDate=" + validityDate +
                ", registrationTime=" + registrationTime +
                ", price=" + price +
                ", photoPath='" + photoPath + '\'' +
                ", stringPhoto='" + stringPhoto + '\'' +
                '}';
    }
}
