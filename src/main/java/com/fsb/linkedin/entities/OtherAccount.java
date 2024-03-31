package com.fsb.linkedin.entities;

import com.fsb.linkedin.utils.MediaConverter;
import javafx.scene.image.Image;

import java.sql.Blob;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OtherAccount {
    public OtherAccount() {}

    private static OtherAccount instance;
    private String gender;



    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    private String type;
    private String website;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
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

    private Blob videoCV;

    public Blob getVideoCV() {
        return videoCV;
    }
    public Image getProfileImage(){
        return new Image(MediaConverter.convertByteArrayToInputStream(getProfilePicture()));
    }
    public void setVideoCV(Blob videoCV) {
        this.videoCV = videoCV;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    public static void setInstance(OtherAccount instance) {
        OtherAccount.instance = instance;
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

    public static OtherAccount getInstance(){
        if (instance == null)
            try{
                instance = new OtherAccount();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        return instance;
    }

    @Override
    public String toString() {
        return "OtherAccount{" +
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
