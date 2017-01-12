package com.languagelearn.test;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@RunWith(Suite.class)
@TestPropertySource("/test-application.properties")

@ContextConfiguration("/test-spring.xml") //TODO: needed here??

@Suite.SuiteClasses({
//        TestDealOfferDomain.class,
//        TestBookingDomain.class,
//        TestFileDomain.class,
//        TestUserDomain.class,
//        TestVendorDomain.class
})
public class MainTestSuite {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;


    @Before
    public void setup() {
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

    }

}