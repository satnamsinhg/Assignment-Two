package com.assignment.weekTwo.service;

import com.assignment.weekTwo.Model.COrder;
import com.assignment.weekTwo.Repository.OrderRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderRespository orderRespository;

    public ResponseEntity<COrder> saveOrder(@Valid COrder COrder){
        return orderRespository.saveOrder(COrder);
    }

    public ResponseEntity<COrder> getOrder(int id){
        return orderRespository.viewOrder(id);
    }

    public List getAllOrders(){
        return orderRespository.getAllOrders();
    }

    public String deleteOrder(int id){
        return orderRespository.deleteOrder(id);
    }

    public String updateOrder(COrder order){
        return orderRespository.updateOrder(order);
    }

}
