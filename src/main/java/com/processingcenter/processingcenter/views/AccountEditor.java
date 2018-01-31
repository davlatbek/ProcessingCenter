package com.processingcenter.processingcenter.views;

import com.processingcenter.processingcenter.entity.Account;
import com.processingcenter.processingcenter.repositories.AccountRepository;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by davlet on 1/31/18.
 */
@SpringComponent
@UIScope
public class AccountEditor extends VerticalLayout {
    private final AccountRepository accountRepository;

    /**
     * The currently edited account
     */
    private Account account;

    @Autowired
    public AccountEditor(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
}
