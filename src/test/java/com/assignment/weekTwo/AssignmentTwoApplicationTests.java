package com.assignment.weekTwo;

import com.assignment.weekTwo.Model.COrder;
import com.assignment.weekTwo.Repository.OrderRespository;
import com.assignment.weekTwo.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class AssignmentTwoApplicationTests {

	@Autowired
	private OrderService orderService;

	@MockBean
	private OrderRespository respository;

	@Test
	void getAllOrderTest(){
		Timestamp timestamp = new Timestamp(2020,01,04,00,00,000,00);
		COrder order = new COrder(1 , COrder.Status.CANCELLED, timestamp, "Satnam Singh", "New York", "suit watch", "50");
		COrder order1 = new COrder(2 , COrder.Status.SHIPPED, timestamp, "Tejinder Singh", "New York", "Armani code", "700");
		List<COrder> orderList = new ArrayList<>();
		orderList.add(order);
		orderList.add(order1);
		when(respository.getAllOrders())
				.thenReturn(orderList);
		assertEquals(2, orderService.getAllOrders().size());
	}

	@Test
	void getOrderTest(){
		Timestamp timestamp = new Timestamp(2020,01,04,00,00,000,00);
		COrder order = new COrder(1, COrder.Status.CANCELLED, timestamp, "Satnam Singh", "New York", "suit watch", "50");
		when(respository.viewOrder(1))
				.thenReturn(ResponseEntity.status(HttpStatus.OK).body(order));
		assertEquals(ResponseEntity.status(HttpStatus.OK).body(order), orderService.getOrder(1));
	}

	@Test
	void saveOrderTest(){
		Timestamp timestamp = new Timestamp(2020,01,04,00,00,000,00);
		COrder order = new COrder(1, COrder.Status.CANCELLED, timestamp, "Satnam Singh", "New York", "suit watch", "50");
		when(respository.saveOrder(order)).thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(order));
		assertEquals(ResponseEntity.status(HttpStatus.CREATED).body(order), orderService.saveOrder(order));
	}

	@Test
	void deleteOrderTest() {
		int id = 1;
		when(respository.deleteOrder(id)).thenReturn("id is deleted : " + id);
		assertEquals("id is deleted : " + id, orderService.deleteOrder(id));
		verify(respository, times(1)).deleteOrder(id);
	}

	@Test
	void updateOrderTest(){
		int id = 1;
		Timestamp timestamp = new Timestamp(2020,01,04,00,00,000,00);
		COrder order = new COrder(id, COrder.Status.CANCELLED, timestamp, "Satnam Singh", "New York", "suit watch", "50");
		when(respository.updateOrder(order)).thenReturn("Order with id : " + order.getOrderId() + " is updated.");
		assertEquals("Order with id : " + order.getOrderId() + " is updated.", orderService.updateOrder(order));
	}
}
