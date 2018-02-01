package com.processingcenter.processingcenter;

import com.processingcenter.processingcenter.config.AppConfiguration;
import com.processingcenter.processingcenter.entity.Account;
import com.processingcenter.processingcenter.repositories.AccountRepository;
import com.processingcenter.processingcenter.repositories.TransactionRepository;
import com.processingcenter.processingcenter.services.PaymentService;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.testng.Assert;
import org.testng.annotations.*;

@WebAppConfiguration
@ContextConfiguration(classes = AppConfiguration.class)
@ComponentScan
@SpringBootTest
@EnableTransactionManagement
@EnableWebMvc
public class ProcessingcenterApplicationTests extends AbstractTestNGSpringContextTests {

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	TransactionRepository transactionRepository;

	private BasicDataSource dataSource;

	public static final String USERNAME1 = "Vladimir";
	public static final String USERLASTNAME1 = "Ivanov";
	public static final String USERNAME2 = "Ekaterina";
	public static final String USERLASTNAME2 = "Popova";
	public Account account1;
	public Account account2;

	PaymentService paymentService;

	@BeforeTest
	public void beforeTest() {
		dataSource = new BasicDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl("jdbc:postgresql://localhost:5432/pcdb");
		dataSource.setUsername("postgres");
		dataSource.setPassword("1");
		paymentService = new PaymentService();
	}

	@BeforeMethod
	public void beforeMethod(){
        paymentService.setAccountRepository(this.accountRepository);
        paymentService.setTransactionRepository(this.transactionRepository);
    }

	@Test
	public void addAccountTest() {

		int numberOfRowsInTable = JdbcTestUtils.countRowsInTableWhere(new JdbcTemplate(dataSource),
				"account" , null);

		//create account and check if they are added
		account1 = new Account(USERNAME1, USERLASTNAME1, 1000);
		Long id1 = accountRepository.save(account1).getAccId();
		Account addedAccount = accountRepository.findByAccId(id1);

		//check if newly added accounts present in db table
        Assert.assertEquals(account1.getFirstName(), addedAccount.getFirstName());
        Assert.assertEquals(account1.getLastName(), addedAccount.getLastName());
        Assert.assertEquals(account1.getBalance(), addedAccount.getBalance());

		//check if after adding number of records are increased by two
		Assert.assertEquals(numberOfRowsInTable + 1, accountRepository.findAll().size());
	}

	@Test
	public void accountDeleteTest(){

        //create two new users and save them to db
        account1 = new Account(USERNAME1, USERLASTNAME1, 1000);

        Long idToDelete = accountRepository.save(account1).getAccId();
		int numberOfRowsInTable = JdbcTestUtils.countRowsInTableWhere(new JdbcTemplate(dataSource),
				"account" , null);

		//before deleting, let's check if account of id = 1 present in db table
		Account accountIdToDelete = accountRepository.findByAccId(idToDelete);
		Assert.assertEquals(idToDelete, accountIdToDelete.getAccId());

		//create first and second account and check if they are added
		account1 = accountRepository.findByAccId(idToDelete);
		accountRepository.delete(account1);
		Account deletedAccount = accountRepository.findByAccId(idToDelete);

		//check if we actually deleted account with given id
		Assert.assertNull(deletedAccount);
		Assert.assertEquals(numberOfRowsInTable - 1, accountRepository.findAll().size());
	}

	@Test
	public void paymentWithSufficientFundsAccountTest(){

        //create two new users and save them to db
        account1 = new Account(USERNAME1, USERLASTNAME1, 1000);
        account2 = new Account(USERNAME2, USERLASTNAME2, 1000);

        Long userid1 = accountRepository.save(account1).getAccId();
        Long userid2 = accountRepository.save(account2).getAccId();

        //transfer all money user1 has in account
        paymentService.makePayment(userid1, userid2, accountRepository.findByAccId(userid1).getBalance());

        //check if balance of user1 in db  equals 0 now
        Assert.assertEquals((int)accountRepository.findByAccId(userid1).getBalance(), 0);
        Assert.assertEquals((int)accountRepository.findByAccId(userid2).getBalance(), 2000);
	}

	@Test
	public void paymentWithInsuffiecientFundsAccountTest(){

        //create two new users and save them to db
        account1 = new Account(USERNAME1, USERLASTNAME1, 1000);
        account2 = new Account(USERNAME2, USERLASTNAME2, 1000);

        Long userid1 = accountRepository.save(account1).getAccId();
        Long userid2 = accountRepository.save(account2).getAccId();

        //transfer more money than user1 has in account
        paymentService.makePayment(userid1, userid2, accountRepository.findByAccId(userid1).getBalance() + 1);

        //check if balances of both account are the same as before
        Assert.assertEquals((int)accountRepository.findByAccId(userid1).getBalance(), 1000);
        Assert.assertEquals((int)accountRepository.findByAccId(userid1).getBalance(), 1000);
	}
}
