package com.processingcenter.processingcenter;


import com.processingcenter.processingcenter.config.AppConfiguration;
import com.processingcenter.processingcenter.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.Assert;
import org.testng.annotations.Test;

@WebAppConfiguration
@ComponentScan
@ContextConfiguration(classes = AppConfiguration.class)
public class ProcessingcenterApplicationTests extends AbstractTestNGSpringContextTests {

	@Autowired
	AccountRepository accountRepository;

	@Test
	public void test1() {
		Assert.assertEquals(1, 1);
	}



}
