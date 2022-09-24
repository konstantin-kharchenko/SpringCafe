package by.kharchenko.springcafe.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;
import java.util.Set;

import static by.kharchenko.springcafe.controller.DbColumn.*;

@Entity
@Table(name = CLIENTS)
public class Client extends AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID_CLIENT)
    private BigInteger idClient;

    @Column(name = LOYALTY_POINTS)
    private int loyaltyPoints;

    @Column(name = BLOCK)
    private boolean block;

    @Column(name = CLIENT_ACCOUNT)
    private BigDecimal clientAccount;

    @OneToOne
    @JoinColumn(name = ID_USER, referencedColumnName = ID_USER)
    protected User user;
    @OneToMany(mappedBy = CLIENT, fetch = FetchType.LAZY)
    private Set<Order> orders;

    public Client() {
        clientAccount = new BigDecimal("0");
    }

    public BigInteger getIdClient() {
        return idClient;
    }

    public void setIdClient(BigInteger idClient) {
        this.idClient = idClient;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(int loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public boolean isBlock() {
        return block;
    }

    public void setBlock(boolean block) {
        this.block = block;
    }

    public BigDecimal getClientAccount() {
        return clientAccount;
    }

    public void setClientAccount(BigDecimal clientAccount) {
        this.clientAccount = clientAccount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return loyaltyPoints == client.loyaltyPoints && block == client.block && Objects.equals(idClient, client.idClient) && Objects.equals(clientAccount, client.clientAccount) && Objects.equals(orders, client.orders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idClient, loyaltyPoints, block, clientAccount, orders);
    }

    @Override
    public String toString() {
        return "Client{" +
                "idClient=" + idClient +
                ", loyaltyPoints=" + loyaltyPoints +
                ", block=" + block +
                ", clientAccount=" + clientAccount +
                '}';
    }
}
