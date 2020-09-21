package com.sdgcrm.application.data.entity;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Product extends Auditable<String>{

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

    private String name;

    private String description;

    private String type;

    private double price;

    private String notes;





    @ManyToOne
    @JoinColumn(name="user_id")
    private User company;


    @ManyToOne
    @JoinColumn(name="invoice_id")
    private Product invoices;




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User getCompany() {
        return company;
    }

    public void setCompany(User company) {
        this.company = company;
    }

    public Product getInvoices() {
        return invoices;
    }

    public void setInvoices(Product invoices) {
        this.invoices = invoices;
    }
}
