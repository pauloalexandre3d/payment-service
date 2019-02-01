package com.project.paymentservice;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
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

import com.project.paymentservice.domain.Buyer;
import com.project.paymentservice.domain.Client;
import com.project.paymentservice.domain.CreditCard;
import com.project.paymentservice.domain.Payment;
import com.project.paymentservice.domain.Payment.PaymentStatus;
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
		//@// @formatter:off
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
				"		\"number\": \"5131778903563981\",\n" + 
				"		\"expirationDate\": \"2022-09\",\n" + 
				"		\"cvv\": \"123\"\n" + 
				"	}\n" + 
				"}";
		// @formatter:on
		MvcResult mvcResult = mvc
				.perform(post("/payment").contentType(MediaType.APPLICATION_JSON_VALUE)
						.accept(MediaType.APPLICATION_JSON_VALUE).content(request))
				.andExpect(status().isOk()).andReturn();
		List<Payment> payments = this.payments.findAll();
		assertThat(mvcResult.getResponse().getContentAsString(), containsString(payments.get(0).getId().toString()));
		assertEquals(1, payments.size());
	}

	@Test
	public void testShouldCreatePaymentWithSlip() throws Exception {
		String request = "{\n" + "	\"client\": {\n" + "		\"id\": \"123456789\"\n" + "	},\n"
				+ "	\"buyer\": {\n" + "		\"name\": \"Paulo Machado\",\n"
				+ "		\"email\": \"paulomachado@project.com\",\n" + "		\"cpf\": \"46434925034\"\n" + "	},\n"
				+ "	\"amount\": 100\n" + "}";
		MvcResult mvcResult = mvc
				.perform(post("/payment/").contentType(MediaType.APPLICATION_JSON_VALUE)
						.accept(MediaType.APPLICATION_JSON_VALUE).content(request))
				.andExpect(status().isOk()).andReturn();
		assertThat(mvcResult.getResponse().getContentAsString(), containsString("1111111 1 2222222 2 333333 3 44444 4"));
		List<Payment> payments = this.payments.findAll();
		assertEquals(1, payments.size());
	}

	@Test
	public void testShouldFindPaymentWithStatus() throws Exception {
		Payment paymentSaved = this.payments.save(getPaymentDummy());
		MvcResult mvcResult = mvc.perform(
				get("/payment/{paymentId}/status", paymentSaved.getId()).accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn();
		assertThat(mvcResult.getResponse().getContentAsString(), containsString("5131778903563981"));
		assertThat(mvcResult.getResponse().getContentAsString(), containsString("APPROVED"));
	}

	@Test
	public void testShouldCreatePaymentWithCreditCardWithoutBuyer() throws Exception {
		String request = "{\n" + "	\"client\": {\n" + "		\"id\": \"123456789\"\n" + "	},\n"
				+ "	\"amount\": 100,\n" + "	\"type\": \"CREDIT_CARD\",\n" + "	\"creditCard\": {\n"
				+ "		\"holderName\": \"Paulo Machado\",\n" + "		\"number\": \"5131778903563981\",\n"
				+ "		\"expirationDate\": \"2022-09\",\n" + "		\"cvv\": \"123\"\n" + "	}\n" + "}";
		mvc.perform(post("/payment").contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE).content(request)).andExpect(status().isBadRequest())
				.andReturn();
	}

	@Test
	public void testShouldCreatePaymentWithCreditCardWithoutBuyerName() throws Exception {
		String request = "{\n" + "	\"client\": {\n" + "		\"id\": \"123456789\"\n" + "	},\n"
				+ "	\"buyer\": {\n" + "		\"email\": \"paulomachado@project.com\",\n"
				+ "		\"cpf\": \"46434925034\"\n" + "	},\n" + "	\"amount\": 100,\n"
				+ "	\"type\": \"CREDIT_CARD\",\n" + "	\"creditCard\": {\n"
				+ "		\"holderName\": \"Paulo Machado\",\n" + "		\"number\": \"5131778903563981\",\n"
				+ "		\"expirationDate\": \"2022-09\",\n" + "		\"cvv\": \"123\"\n" + "	}\n" + "}";
		mvc.perform(post("/payment").contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE).content(request)).andExpect(status().isBadRequest())
				.andReturn();
	}

	@Test
	public void testShouldFindPaymentWithStatusAndPaymentNonexistent() throws Exception {
		mvc.perform(get("/payment/{paymentId}/status", "abobora").accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isNotFound()).andReturn();
	}

	private Payment getPaymentDummy() {
		Client client = new Client("123456789");
		Buyer buyer = new Buyer("paulo machado", "paulomachado@project.com", "46434925034");
		CreditCard creditCard = new CreditCard("paulo machado", "5131778903563981", "08/2022", "736");
		Payment payment = new Payment(null, 100.1, Payment.PaymentType.CREDIT_CARD, client, buyer, creditCard,
				PaymentStatus.APPROVED, "1111111 1 2222222 2 333333 3 44444 4");
		return payment;
	}

}
