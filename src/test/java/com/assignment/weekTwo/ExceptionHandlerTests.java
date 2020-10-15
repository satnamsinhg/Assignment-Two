package com.assignment.weekTwo;

import com.assignment.weekTwo.Model.COrder;
import com.assignment.weekTwo.MyExceptionController.ExceptionResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Timestamp;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExceptionHandlerTests {

    MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void postOrderWithInvalidCustomerName() throws Exception {
        COrder order = new COrder();
        order.setOrderStatus(COrder.Status.PENDING);
        order.setCustomerAddress("New York");
        order.setCustomerName("S");
        order.setOrderBill("400");
        order.setOrderDetails("suit Watch");
        String s = objectMapper.writeValueAsString(order);
        MvcResult mvcResult = this.mockMvc
                .perform(post("/api/order")
                        .content(s)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest()).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        ExceptionResponse response = objectMapper.readValue(contentAsString, ExceptionResponse.class);
        Assertions.assertEquals("uri=/api/order", response.getDetails());
        Assertions.assertEquals("Customer name should be atleast 3 characters long", response.getMessage());
        Assertions.assertNotNull(response.getTimestamp());
    }

    @Test
    public void orderNotFoundExceptionToGetTest() throws Exception {
        int orderId = 100;
        MvcResult mvcResult = mockMvc
                .perform(get("/api/order/{id}", orderId))
                .andExpect(status().isNotFound())
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        ExceptionResponse response = objectMapper.readValue(contentAsString, ExceptionResponse.class);
        Assertions.assertEquals("uri=/api/order/"+orderId, response.getDetails());
        Assertions.assertEquals("Order not found with id : "+orderId, response.getMessage());
        Assertions.assertNotNull(response.getTimestamp());
    }

    @Test
    public void orderNotFoundExceptionToUpdateTest() throws Exception {
        Timestamp timestamp = Timestamp.valueOf("2020-01-02 00:00:00");
        COrder order = new COrder(100 , COrder.Status.SHIPPED, timestamp, "Tejinder Kumar", "New York", "Armani", "700");
        String object = objectMapper.writeValueAsString(order);
        MvcResult mvcResult = this.mockMvc
                .perform(put("/api/order").content(object).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ExceptionResponse response = objectMapper.readValue(content, ExceptionResponse.class);
        Assertions.assertEquals("uri=/api/order", response.getDetails());
        Assertions.assertEquals("Order not found with id : "+order.getOrderId(), response.getMessage());
        Assertions.assertNotNull(response.getTimestamp());
    }

    @Test
    public void orderNotFoundExceptionToDeleteTest() throws Exception {
        int orderId = 100;
        MvcResult mvcResult = this.mockMvc
                .perform(delete("/api/order/" + 100))
                .andExpect(status().isNotFound()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ExceptionResponse response = objectMapper.readValue(content, ExceptionResponse.class);
        Assertions.assertEquals("uri=/api/order/"+orderId, response.getDetails());
        Assertions.assertEquals("Order not found with id : "+orderId, response.getMessage());
        Assertions.assertNotNull(response.getTimestamp());
    }
}
