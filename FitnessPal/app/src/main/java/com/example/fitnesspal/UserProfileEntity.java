package com.example.uee_app;

public class UserProfileEntity {
    private String id;
    private String Name;
    private String Age;
    private String Gender;
    private String Occupation;
    private String Email;

    public UserProfileEntity(){

    }

    public UserProfileEntity(String id, String name, String age, String gender, String occupation, String email) {
        this.id = id;
        Name = name;
        Age = age;
        Gender = gender;
        Occupation = occupation;
        Email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getOccupation() {
        return Occupation;
    }

    public void setOccupation(String occupation) {
        Occupation = occupation;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
