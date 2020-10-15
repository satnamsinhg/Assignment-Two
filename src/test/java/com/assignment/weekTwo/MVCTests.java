package com.assignment.weekTwo;

import com.assignment.weekTwo.Model.COrder;
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
public class MVCTests {
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;
    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    @Autowired
    ObjectMapper objectMapper;
    @Test
    public void testPostOrder() throws Exception {
        COrder order = new COrder();
        order.setOrderStatus(COrder.Status.PENDING);
        order.setCustomerAddress("New York");
        order.setCustomerName("Sartaj Kahlon");
        order.setOrderBill("400");
        order.setOrderDetails("suit Watch");
        String s = objectMapper.writeValueAsString(order);
        MvcResult mvcResult = this.mockMvc
                .perform(post("/api/order")
                        .content(s)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();
       String response = mvcResult.getResponse().getContentAsString();
        COrder readValue = objectMapper.readValue(response, COrder.class);
        Assertions.assertNotNull(readValue);
    }

    @Test
    public void testGetOrder() throws Exception{
        Timestamp timestamp = Timestamp.valueOf("2020-01-01 00:00:00");
        COrder corder = new COrder(1 , COrder.Status.PENDING, timestamp, "Satnam Singh", "New York", "watch", "350");
        MvcResult mvcResult = mockMvc
                .perform(get("/api/order/{id}", corder
                        .getOrderId()))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        COrder order = objectMapper.readValue(contentAsString, COrder.class);
        Assertions.assertEquals(corder.toString(), order.toString());
    }

    @Test
    public void testGetAllOrder() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/api/order"))
                .andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        COrder[] cOrders = objectMapper.readValue(response, COrder[].class);
        Assertions.assertEquals(cOrders.length, 5);
    }

    @Test
    public void testDeleteOrder() throws Exception {
        this.mockMvc
                .perform(delete("/api/order/"+3))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    public void testUpdateOrder() throws Exception {
        Timestamp timestamp = Timestamp.valueOf("2020-01-02 00:00:00");
        COrder order = new COrder(2 , COrder.Status.SHIPPED, timestamp, "Tejinder Kumar", "New York", "Armani", "700");
        String object = objectMapper.writeValueAsString(order);
        MvcResult mvcResult = this.mockMvc
                .perform(put("/api/order").content(object).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        Assertions.assertEquals("Order with id : "+ order.getOrderId() +" is updated.", content);
    }
}