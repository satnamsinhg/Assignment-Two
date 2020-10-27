package cucumberJava.cucumberJava;

import com.assignment.weekTwo.Model.COrder;
import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import lombok.extern.log4j.Log4j2;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.stream.Collectors;

@Log4j2
@RunWith(SpringRunner.class)
@SpringBootTest
public class StepDefinition {

    //The Application should be up and running

    @LocalServerPort
    private int port = 8090;

    @Autowired
    ObjectMapper objectMapper;

    private RestTemplate restTemplate = new RestTemplate();
    private String postUrl = "http://localhost";
    String url = postUrl + ":" + port + "/api/order";

    @Given("^Send Post Http Request on /api/orders with Order$")
    public void send_Post_Http_Request_on_api_orders_with_Order() throws Throwable {
        System.out.println("post Method");
        COrder cOrder = new COrder();
        cOrder.setOrderStatus(COrder.Status.PENDING);
        cOrder.setCustomerName("Sartaj Kahlon");
        cOrder.setCustomerAddress("Canada");
        cOrder.setOrderDetails("iPad");
        cOrder.setOrderBill("700");
        restTemplate.postForEntity(url, cOrder, COrder.class);
    }

    @Then("^Order should add into Order List$")
    public void order_should_add_into_Order_List() throws Throwable {
        System.out.println("testing request back");
        COrder cOrder = new COrder();
        cOrder.setOrderStatus(COrder.Status.PENDING);
        cOrder.setCustomerName("Sartaj Kahlon");
        cOrder.setCustomerAddress("Canada");
        cOrder.setOrderDetails("iPad");
        cOrder.setOrderBill("700");

        ResponseEntity<COrder[]> entity = restTemplate.getForEntity(url, COrder[].class);

        COrder cOrder1 = Arrays
                .stream(entity.getBody())
                .skip(entity.getBody().length - 1)
                .collect(Collectors.toList())
                .stream().findFirst().get();

        System.out.println(cOrder1.getCustomerName());


        Assert.assertTrue( cOrder1.getCustomerName().equals(cOrder.getCustomerName()));
        Assert.assertTrue(cOrder1.getOrderStatus().equals(cOrder.getOrderStatus()));
        Assert.assertTrue(cOrder1.getOrderDetails().equals(cOrder.getOrderDetails()));
        Assert.assertTrue(cOrder1.getOrderBill().equals(cOrder.getOrderBill()));
        Assert.assertTrue(cOrder1.getCustomerAddress().equals(cOrder.getCustomerAddress()));
    }

    @Given("^Send Delete Http Request on /api/orders with OrderId$")
    public void send_Delete_Http_Request_on_api_orders_with_OrderId() throws Throwable {
        int id = 2;
        restTemplate.delete(url+"/"+id);
    }

    @Then("^Order should delete from Order List$")
    public void order_should_delete_from_Order_List() throws Throwable {

        int id = 2;

        try{
            restTemplate.getForEntity(url+"/"+id, COrder.class);
        } catch (HttpClientErrorException e){

            System.out.println(e.getStatusCode());
            Assert.assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);

        }
    }
}