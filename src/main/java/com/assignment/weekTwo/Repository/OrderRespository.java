package com.assignment.weekTwo.Repository;

import com.assignment.weekTwo.Model.COrder;
import com.assignment.weekTwo.MyExceptionController.OrderNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Repository
@Transactional
public class OrderRespository {

    @Autowired
    EntityManager entityManager;

    public ResponseEntity<COrder> saveOrder(@Valid COrder COrder){
        entityManager.persist(COrder);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(COrder.getOrderId()).toUri();
        var responseEntity = ResponseEntity.created(uri).body(COrder);
        return responseEntity;
    }

    public ResponseEntity<COrder> viewOrder(int orderId){
        COrder COrder = entityManager.find(COrder.class, orderId);
        if (COrder == null){
            throw new OrderNotFoundException(orderId);
        }
        var responseEntity = ResponseEntity.ok().body(COrder);
        return responseEntity;
    }

    public List getAllOrders(){
        Query nativeQuery = entityManager.createQuery("SELECT a FROM COrder a");
        return nativeQuery.getResultList();
    }

    public String deleteOrder(int id){
        COrder cOrder = entityManager.find(COrder.class, id);
        if (cOrder == null) throw new OrderNotFoundException(id);
        entityManager.remove(cOrder);
        return id + " is removed.";
    }

    public String updateOrder(COrder order) {
        if (entityManager.find(COrder.class, order.getOrderId()) == null){
            throw new OrderNotFoundException(order.getOrderId());
        }
        entityManager.merge(order);
        return "Order with id : " + order.getOrderId() + " is updated.";
    }
}
