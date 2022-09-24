package by.kharchenko.springcafe.model.entity;

import by.kharchenko.springcafe.validator.annotation.CustomFutureOrPresent;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

import static by.kharchenko.springcafe.controller.DbColumn.*;

@Entity
@Table(name = ORDERS)
public class Order extends AbstractEntity implements Serializable, Comparable<Order> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID_ORDER)
    private BigInteger idOrder;

    @Column(name = NAME)
    @NotEmpty(message = "name must not be empty")
    @Pattern(regexp = "^[А-Яа-яA-Za-z0-9-_\\s]{3,}$", message = "Invalid")
    private String name;


    @Temporal(TemporalType.DATE)
    @Column(name = DATE_OF_RECEIVING)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "date must not be null")
    @CustomFutureOrPresent
    private Date dateOfReceiving;

    @Column(name = PRICE)
    private BigDecimal price;

    @Column(name = RECEIVED)
    private boolean received;

    @ManyToOne
    @JoinColumn(name = ID_CLIENT)
    private Client client;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = ID_PAYMENT_TYPE)
    @NotNull(message = "Payment type must not be empty")
    private PaymentType paymentType;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = ORDERS_PRODUCTS, joinColumns = @JoinColumn(name = ID_ORDER), inverseJoinColumns = @JoinColumn(name = ID_PRODUCT))
    private Set<Product> products;

    public Order() {
        price = new BigDecimal("0");
    }

    public BigInteger getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(BigInteger idOrder) {
        this.idOrder = idOrder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateOfReceiving() {
        return dateOfReceiving;
    }

    public void setDateOfReceiving(Date dateOfReceiving) {
        this.dateOfReceiving = dateOfReceiving;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isReceived() {
        return received;
    }

    public void setReceived(boolean received) {
        this.received = received;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    @Override
    public int compareTo(Order o) {
        return this.dateOfReceiving.compareTo(o.dateOfReceiving);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return received == order.received && Objects.equals(idOrder, order.idOrder) && Objects.equals(name, order.name) && Objects.equals(dateOfReceiving, order.dateOfReceiving) && Objects.equals(price, order.price)  && Objects.equals(paymentType, order.paymentType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idOrder, name, dateOfReceiving, price, received, paymentType);
    }

    @Override
    public String toString() {
        return "Order{" +
                "idOrder=" + idOrder +
                ", name='" + name + '\'' +
                ", dateOfReceiving=" + dateOfReceiving +
                ", price=" + price +
                ", received=" + received +
                ", paymentType=" + paymentType +
                '}';
    }


}
