package com.processingcenter.processingcenter.views;

import com.processingcenter.processingcenter.entity.Account;
import com.processingcenter.processingcenter.entity.Transaction;
import com.processingcenter.processingcenter.repositories.AccountRepository;
import com.processingcenter.processingcenter.repositories.TransactionRepository;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by davlet on 1/31/18.
 */
@SpringUI
@Theme("valo")
public class MainUI extends UI{
    public Navigator navigator;
    AccountRepository accountRepository;
    Grid<Account> accountGrid;
    TransactionRepository transactionRepository;
    Grid<Transaction> transactionGrid;
    TextField filterByLastName;
    Button addNewAccountBtn, addNewTransactionBtn;
    TextField fromAccount, toAccount, amount;

    @Autowired
    public MainUI(Component content, AccountRepository accountRepository, TransactionRepository transactionRepository) {
        super(content);
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        Label title = new Label("Processing Center");
        this.fromAccount.setPlaceholder("from account id");
        this.toAccount.setPlaceholder("to account id");
        this.amount.setPlaceholder("amount");
        filterByLastName.setPlaceholder("Filter by last name");
        filterByLastName.setValueChangeMode(ValueChangeMode.LAZY);
    }
}
