package com.fsb.linkedin.entities;

import com.fsb.linkedin.utils.ImageConverter;
import javafx.scene.image.Image;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PersonalAccount {
    public PersonalAccount() {}

    private static PersonalAccount instance;
    private String gender;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private LocalDate dateOfBirth;
    private String country;
    private String password;
    private List<Qualification> qualifications = new ArrayList<>();
    private List<Experience> experiences = new ArrayList<>();
    private List<Project> projects = new ArrayList<>();
    private byte[] profilePicture;

    private Image profileImage;

    public void setProfileImage(Image profileImage) {
        this.profileImage = profileImage;
    }

    private byte[] videoCV;

    public byte[] getVideoCV() {
        return videoCV;
    }
    public Image getProfileImage(){
        return new Image(ImageConverter.convertByteArrayToInputStream(getProfilePicture()));
    }
    public void setVideoCV(byte[] videoCV) {
        this.videoCV = videoCV;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    public static void setInstance(PersonalAccount instance) {
        PersonalAccount.instance = instance;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Qualification> getQualifications() {
        return qualifications;
    }

    public void setQualifications(List<Qualification> qualifications) {
        this.qualifications = qualifications;
    }

    public List<Experience> getExperiences() {
        return experiences;
    }

    public void setExperiences(List<Experience> experiences) {
        this.experiences = experiences;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public static PersonalAccount getInstance(){
        if (instance == null)
            try{
                instance = new PersonalAccount();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        return instance;
    }

    @Override
    public String toString() {
        return "PersonalAccount{" +
                "gender='" + gender + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", country='" + country + '\'' +
                ", password='" + password + '\'' +
                ", qualifications=" + qualifications +
                ", experiences=" + experiences +
                ", projects=" + projects +
                '}';
    }


}
