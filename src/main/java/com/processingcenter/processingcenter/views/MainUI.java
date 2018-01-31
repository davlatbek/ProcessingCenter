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
    Label title;

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
        this.title = new Label("PROCESSING CENTER");
        filterByLastName.addValueChangeListener(x -> listAccounts(x.getValue()));
        accountGrid.setColumns("accId", "firstName", "lastName", "balance");
        transactionGrid.setColumns("trxId", "from_id", "to_id", "amount");
        addNewTransactionBtn.addClickListener(x -> addNewTransaction(fromAccount.getValue(), toAccount.getValue(), amount.getValue()));

        HorizontalLayout actionsAcc = new HorizontalLayout(filterByLastName, addNewAccountBtn);
        HorizontalLayout actionsTrx = new HorizontalLayout(fromAccount, toAccount, amount);
        VerticalLayout accountsWithActions = new VerticalLayout(actionsAcc, accountGrid);
        VerticalLayout transactionsWithActions = new VerticalLayout(actionsTrx, addNewTransactionBtn, transactionGrid);
        HorizontalLayout accountsAndTransactions = new HorizontalLayout(accountsWithActions, transactionsWithActions);
        VerticalLayout mainLayout = new VerticalLayout(title, accountsAndTransactions);
        setContent(mainLayout);

        listAccounts(null);
        listTransactions();
        filterByLastName.setValue(accountRepository.findBalanceByAccId(1L).toString());
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

    private void addNewTransaction(String fromid, String toid, String amountOfMoney) {
        transactionRepository.save(new Transaction(Long.parseLong(fromid), Long.parseLong(toid), Integer.parseInt(amountOfMoney)));
        listTransactions();
        fromAccount.clear();
        toAccount.clear();
        amount.clear();
        Notification.show("Transaction succeed");
    }

    private void listTransactions(){
        transactionGrid.setItems(transactionRepository.findAll());
    }
}
