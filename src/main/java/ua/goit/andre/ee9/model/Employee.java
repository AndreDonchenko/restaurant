package ua.goit.andre.ee9.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.sql.Date;
import javax.persistence.*;


/**
 * Created by Andre on 28.05.2016.
 */
@Entity
@Table(name = "employee")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Employee {

    @Id
    @SequenceGenerator(name="employee_id_seq",
            sequenceName="employee_id_seq",
            allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator="employee_id_seq")
    @Column(name = "id", updatable=false)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "birth_date")
    private Date birthDay;

    @Column(name = "phone")
    private String phone;

    public String getPhotoFn() {
        return photoFn;
    }

    public void setPhotoFn(String photoFn) {
        this.photoFn = photoFn;
    }

    @Column(name = "salary")
    private double salary;

    @Column(name = "photo_fn")
    private String photoFn;

    @ManyToOne
    @JoinColumn (name = "position_id")
    @Fetch(FetchMode.JOIN)
    private Position position;

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public double getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return name + " " + surname;
    }

    /*
        @Override
        public String toString() {
            return name + " " + surname;
        }
    */
    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
