package by.kharchenko.springcafe.model.entity;



import javax.validation.constraints.*;

import by.kharchenko.springcafe.validator.annotation.Birthday;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.Objects;

import static by.kharchenko.springcafe.controller.DbColumn.*;

@Entity
@Table(name = USERS)
@Inheritance
public class User extends AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID_USER)
    private BigInteger idUser;

    @Column(name = NAME)
    @NotEmpty(message = "name must not be empty")
    @Size(min = 4, max = 16, message = "name must be between 4 and 16 characters")
    @Pattern(regexp = "^[A-Z][a-z]+", message = "Invalid")
    private String name;

    @Column(name = SURNAME)
    @NotEmpty(message = "surname must not be empty")
    @Size(min = 4, max = 16, message = "surname must be between 4 and 16 characters")
    @Pattern(regexp = "^[A-Z](([a-z]{1,}(['-]*)[a-z]*)|(['-][a-z]+))", message = "Invalid")
    private String surname;

    @Column(name = LOGIN)
    @NotEmpty(message = "login must not be empty")
    @Pattern(regexp = "^[A-Za-z0-9-_]{3,}$", message = "Invalid")
    private String login;

    @Column(name = PASSWORD)
    @NotEmpty(message = "password must not be empty")
    @Pattern(regexp = ".*[A-Za-z.-_*].*", message = "Invalid")
    private String password;

    @Temporal(TemporalType.DATE)
    @Column(name = BIRTHDAY)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "birthday must not be null or empty")
    @Birthday(year = 12, message = "year more then 12")
    private Date birthday;

    @Temporal(TemporalType.DATE)
    @Column(name = REGISTRATION_TIME)
    @DateTimeFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    private Date registrationTime;

    @Column(name = PHONE_NUMBER)
    @Pattern(regexp = "\\+375(33|29|25|44)\\d{7}", message = "Invalid")
    @NotEmpty(message = "phone number must not be empty")
    private String phoneNumber;

    @Column(name = EMAIL)
    @NotEmpty(message = "email must not be empty")
    @Email(message = "email must be valid")
    private String email;

    @Column(name = PHOTO_PATH)
    @Pattern(regexp = "^.+\\.(jpg|jpeg|raw|png)$", message = "Invalid")
    private String photoPath;

    transient private String stringPhoto;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = ID_ROLE)
    private Role role;

    @OneToOne(mappedBy = USER)
    private Administrator administrator;

    @OneToOne(mappedBy = USER)
    private Client client;

    public BigInteger getIdUser() {
        return idUser;
    }

    public void setIdUser(BigInteger idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(Date registrationTime) {
        this.registrationTime = registrationTime;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Administrator getAdministrator() {
        return administrator;
    }

    public void setAdministrator(Administrator administrator) {
        this.administrator = administrator;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(idUser, user.idUser) && Objects.equals(name, user.name) && Objects.equals(surname, user.surname) && Objects.equals(login, user.login) && Objects.equals(password, user.password) && Objects.equals(birthday, user.birthday) && Objects.equals(registrationTime, user.registrationTime) && Objects.equals(phoneNumber, user.phoneNumber) && Objects.equals(email, user.email) && Objects.equals(photoPath, user.photoPath) && Objects.equals(stringPhoto, user.stringPhoto) && role == user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser, name, surname, login, password, birthday, registrationTime, phoneNumber, email, photoPath, stringPhoto, role);
    }

    @Override
    public String toString() {
        return "User{" +
                "idUser=" + idUser +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", birthday=" + birthday +
                ", registrationTime=" + registrationTime +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", photoPath='" + photoPath + '\'' +
                ", stringPhoto='" + stringPhoto + '\'' +
                ", role=" + role +
                '}';
    }
}
