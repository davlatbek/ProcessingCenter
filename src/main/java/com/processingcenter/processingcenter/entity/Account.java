package com.processingcenter.processingcenter.entity;

import javax.persistence.*;

/**
 * Created by davlet on 1/31/18.
 * Таблица представляет собой объект банковский счет клиента (аккаунт)
 */

@Entity
@Table(name = "account")
public class Account {

    private Long accId;
    private String firstName;
    private String lastName;
    private Integer balance;

    protected Account() {
    }

    public Account(String firstName, String lastName, Integer balance) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.balance = balance;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public Long getAccId() {
        return accId;
    }

    public void setAccId(Long accId) {
        this.accId = accId;
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

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accId=" + accId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", balance=" + balance +
                '}';
    }
}

