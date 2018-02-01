package com.processingcenter.processingcenter;

import com.processingcenter.processingcenter.config.AppConfiguration;
import com.processingcenter.processingcenter.entity.Account;
import com.processingcenter.processingcenter.repositories.AccountRepository;
import com.processingcenter.processingcenter.repositories.TransactionRepository;
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
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

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

	@Autowired
	WebApplicationContext context;

	public static final String USERNAME1 = "Vladimir";
	public static final String USERLASTNAME1 = "Ivanov";
	public static final String USERNAME2 = "Ekaterina";
	public static final String USERLASTNAME2 = "Popova";
	public static final Long ACCOUNT_ID1 = 1000L;
	public static final Long ACCOUNT_ID2 = 1001L;
	public Account account1;
	public Account account2;

	@BeforeTest
	public void setup() {
		dataSource = new BasicDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl("jdbc:postgresql://localhost:5432/pcdb");
		dataSource.setUsername("postgres");
		dataSource.setPassword("1");
	}

	@Test
	public void addAccountTest() {

		//first delete if exists
		JdbcTestUtils.deleteFromTableWhere(new JdbcTemplate(dataSource),
				"account",
				"accid in(?, ?)",
				ACCOUNT_ID1,
				ACCOUNT_ID2);
		Assert.assertEquals(0,
				JdbcTestUtils.countRowsInTableWhere(new JdbcTemplate(dataSource),
				"account", "accid in('" + ACCOUNT_ID1 + "', '" + ACCOUNT_ID2 + "')"));

		int numberOfRowsInTable = JdbcTestUtils.countRowsInTableWhere(new JdbcTemplate(dataSource),
				"account" , null);

		//create first and second account and check if they are added
		account1 = new Account(USERNAME1, USERLASTNAME1, 1000);
		account2 = new Account(USERNAME2, USERLASTNAME2, 2000);
		accountRepository.save(account1);
		accountRepository.save(account2);

		Assert.assertEquals(numberOfRowsInTable + 2, accountRepository.findAll().size());
	}

	@Test
	public void accountDeleteTest(){

	}

	@Test
	public void paymentWithSufficientFundsAccountTest(){

	}

	@Test
	public void paymentWithInsuffiecientFundsAccountTest(){

	}
}
