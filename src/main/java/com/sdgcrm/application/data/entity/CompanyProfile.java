package com.sdgcrm.application.data.entity;


import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class CompanyProfile extends Auditable<String>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    private String Name;

    @NotNull
    private String Location= "";

    @NotNull
    private String website="";

    @NotNull
    private String sector;


    @Lob
    @Column(length=100000)
    private byte[] profileImg;

    @NotNull
    private String facebook="";
    @NotNull
    private String twitter="";
    @NotNull
    private String instagram="";

    private long phone;


    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy="company")
    private List<Customer> client = new LinkedList<>();

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy="company")
    private List<Product> product  = new LinkedList<>();

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy="employer")
    private List<Employee> employer = new LinkedList<>();


    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy="company")
    private List<Asset> properties = new LinkedList<>();




    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public byte[] getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(byte[] profileImg) {
        this.profileImg = profileImg;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public CompanyProfile(String name, String location, String website, String sector) {
        Name = name;
        Location = location;
        this.website = website;
        this.sector = sector;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }


    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public CompanyProfile() {

    }


    public List<Asset> getProperties() {
        return properties;
    }

    public void setProperties(List<Asset> properties) {
        this.properties = properties;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

