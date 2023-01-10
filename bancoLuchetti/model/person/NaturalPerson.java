package br.com.alexandre.bancoLuchetti.model.person;

import java.time.LocalDate;

/**
 *
 * @author Alexandre
 */
public class NaturalPerson {

    private String firstName;
    private String lastName;
    private String fullName;
    private String cpf;
    private String gender;
    private LocalDate dateOfBirth;
    private int age;
    private String fathersName;
    private String fathersCpf;
    private String mothersName;
    private String mothersCpf;

    public NaturalPerson(String firstName, String lastName, String cpf, String gender, LocalDate dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.cpf = cpf;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setFullName(String firstName, String lastName) {
        this.fullName = firstName + " " + lastName;
    }

    public void setFathersName(String fathersName) {
        this.fathersName = fathersName;
    }

    public void setFathersCpf(String fathersCpf) {
        this.fathersCpf = fathersCpf;
    }

    public void setMothersName(String mothersName) {
        this.mothersName = mothersName;
    }

    public void setMothersCpf(String mothersCpf) {
        this.mothersCpf = mothersCpf;
    }

    public int getAge() {
        return age;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getCpf() {
        return cpf;
    }

    public String getGender() {
        return gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getFathersName() {
        return fathersName;
    }

    public String getFathersCpf() {
        return fathersCpf;
    }

    public String getMothersName() {
        return mothersName;
    }

    public String getMothersCpf() {
        return mothersCpf;
    }
}