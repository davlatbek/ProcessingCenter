package com.processingcenter.processingcenter.views;

import com.processingcenter.processingcenter.entity.Account;
import com.processingcenter.processingcenter.entity.Transaction;
import com.processingcenter.processingcenter.repositories.AccountRepository;
import com.processingcenter.processingcenter.repositories.TransactionRepository;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

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
    public MainUI(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.accountGrid = new Grid<Account>(Account.class);
        this.accountGrid.setCaption("Bank Accounts");
        this.transactionGrid = new Grid<>(Transaction.class);
        this.transactionGrid.setCaption("Last Transactions");
        this.filterByLastName = new TextField();
        this.addNewAccountBtn = new Button("Add new account", FontAwesome.PLUS);
        this.addNewTransactionBtn = new Button("Make transfer", FontAwesome.PLUS);
        this.fromAccount = new TextField();
        this.toAccount = new TextField();
        this.amount = new TextField();
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        Label title = new Label("PROCESSING CENTER");
        this.fromAccount.setPlaceholder("from account id");
        this.toAccount.setPlaceholder("to account id");
        this.amount.setPlaceholder("amount");
        filterByLastName.setPlaceholder("Filter by last name");
        filterByLastName.setValueChangeMode(ValueChangeMode.LAZY);
    }

    private void listAccounts(String filter){
        if (StringUtils.isEmpty(filter))
            accountGrid.setItems(accountRepository.findAll());
        else
            accountGrid.setItems(accountRepository.findAllByLastNameStartsWithIgnoreCase(filter));
    }

    private void listTransactions(){
        transactionGrid.setItems(transactionRepository.findAll());
    }
}
