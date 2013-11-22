package com.communityhelper;


import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;

@DbUnitConfiguration(dataSetLoader=YamlDataSetLoader.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext*.xml"})
@TransactionConfiguration(defaultRollback=true,transactionManager="transactionManager")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,DirtiesContextTestExecutionListener.class,
    TransactionDbUnitTestExecutionListener.class})
@Ignore
public class TestEnviroment {
}
