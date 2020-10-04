package com.sdgcrm.application.data.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
public class CreditPayment {

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



    private Double repaymentAmount;
    private Date repaymentDate;

    @ManyToOne
    @JoinColumn(name="client_id")
    private Credit creditor;


    public Double getRepaymentAmount() {
        return repaymentAmount;
    }

    public void setRepaymentAmount(Double repaymentAmount) {
        this.repaymentAmount = repaymentAmount;
    }

    public Date getRepaymentDate() {
        return repaymentDate;
    }

    public void setRepaymentDate(Date repaymentDate) {
        this.repaymentDate = repaymentDate;
    }

    public Credit getCreditor() {
        return creditor;
    }

    public void setCreditor(Credit creditor) {
        this.creditor = creditor;
    }

    public CreditPayment(Double repaymentAmount, Credit creditor) {
        this.repaymentAmount = repaymentAmount;

        this.creditor = creditor;
    }

    public CreditPayment() {
      
    }
}
