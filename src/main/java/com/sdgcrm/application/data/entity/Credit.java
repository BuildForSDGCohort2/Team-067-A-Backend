package com.sdgcrm.application.data.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Credit {

    @Id
    @GeneratedValue(
            strategy= GenerationType.AUTO,
            generator="native"
    )
    @GenericGenerator(
            name = "native",
            strategy = "native"
    )
    private Long id;

    private String creditorName;

    private double totalAmount;

    private Date dateDue;


    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy="creditor")
    private List<CreditPayment> client = new LinkedList<>();


    public String getCreditorName() {
        return creditorName;
    }

    public void setCreditorName(String creditorName) {
        this.creditorName = creditorName;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Date getDateDue() {
        return dateDue;
    }

    public void setDateDue(Date dateDue) {
        this.dateDue = dateDue;
    }

    public List<CreditPayment> getClient() {
        return client;
    }

    public void setClient(List<CreditPayment> client) {
        this.client = client;
    }
}
