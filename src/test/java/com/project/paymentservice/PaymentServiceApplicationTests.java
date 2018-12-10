package com.project.paymentservice;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.project.paymentservice.domain.Payment;
import com.project.paymentservice.repository.Payments;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration("server.port=0")
public class PaymentServiceApplicationTests {

	@Autowired
    private WebApplicationContext context;
	
	private MockMvc mvc;

	@Autowired
	private Payments payments;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
        payments.deleteAll();
    }

	@Test
	public void testShouldCreatePaymentWithCreditCard() throws Exception {
		String request = "{\n" + 
				"	\"client\": {\n" + 
				"		\"id\": \"123456789\"\n" + 
				"	},\n" + 
				"	\"buyer\": {\n" + 
				"		\"name\": \"Paulo Machado\",\n" + 
				"		\"email\": \"paulomachado@project.com\",\n" + 
				"		\"cpf\": \"46434925034\"\n" + 
				"	},\n" + 
				"	\"amount\": 100,\n" + 
				"	\"type\": \"CREDIT_CARD\",\n" + 
				"	\"creditCard\": {\n" + 
				"		\"holderName\": \"Paulo Machado\",\n" + 
				"		\"number\": \"1111 2222 3333 4444\",\n" + 
				"		\"expirationDate\": \"2022-09\",\n" + 
				"		\"cvv\": \"123\"\n" + 
				"	}\n" + 
				"}";
		MvcResult mvcResult = mvc.perform(post("/payment")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(request))
                .andExpect(status().isOk())
                .andReturn();
		List<Payment> payments = this.payments.findAll();
	    assertEquals(payments.get(0).getId().toString(), mvcResult.getResponse().getContentAsString());
        assertEquals(1, payments.size());
	}
	
	@Test
	public void testShouldCreatePaymentWithSlip() throws Exception {
		String request = "{\n" + 
				"	\"client\": {\n" + 
				"		\"id\": \"123456789\"\n" + 
				"	},\n" + 
				"	\"buyer\": {\n" + 
				"		\"name\": \"Paulo Machado\",\n" + 
				"		\"email\": \"paulomachado@project.com\",\n" + 
				"		\"cpf\": \"46434925034\"\n" + 
				"	},\n" + 
				"	\"amount\": 100\n" + 
				"}";
		MvcResult mvcResult = mvc.perform(post("/payment/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(request))
                .andExpect(status().isOk())
                .andReturn();
	    assertEquals("1111111 1 2222222 2 333333 3 44444 4", mvcResult.getResponse().getContentAsString());
		List<Payment> payments = this.payments.findAll();
        assertEquals(1, payments.size());
	}

}
