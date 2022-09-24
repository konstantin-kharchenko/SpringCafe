package by.kharchenko.springcafe.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Objects;

import static by.kharchenko.springcafe.controller.DbColumn.*;

@Entity
@Table(name = ADMINISTRATORS)
public class Administrator extends AbstractEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID_ADMINISTRATOR)
    private BigInteger idAdministrator;

    @Column(name = EXPERIENCE)
    private double experience;

    @OneToOne
    @JoinColumn(name = ID_USER, referencedColumnName = ID_USER)
    private User user;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = ID_STATUS)
    private Status status;

    public Administrator() {
        status = Status.WAITING;
    }

    public BigInteger getIdAdministrator() {
        return idAdministrator;
    }

    public void setIdAdministrator(BigInteger idAdministrator) {
        this.idAdministrator = idAdministrator;
    }

    public double getExperience() {
        return experience;
    }

    public void setExperience(double experience) {
        this.experience = experience;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Administrator that = (Administrator) o;
        return Double.compare(that.experience, experience) == 0 && Objects.equals(idAdministrator, that.idAdministrator) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAdministrator, experience, status);
    }

    @Override
    public String toString() {
        return "Administrator{" +
                "idAdministrator=" + idAdministrator +
                ", experience=" + experience +
                ", status=" + status +
                '}';
    }
}
