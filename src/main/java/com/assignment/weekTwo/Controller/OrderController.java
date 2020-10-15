package com.assignment.weekTwo.Controller;

import com.assignment.weekTwo.Model.COrder;
import com.assignment.weekTwo.MyExceptionController.ExceptionResponse;
import com.assignment.weekTwo.service.OrderService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@ApiOperation(value = "/api/order", tags = "Customer Order Controller")
@RestController
@RequestMapping(value = "/api/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @ApiOperation(value = "Insert an Order", response = COrder.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS", response = COrder.class),
            @ApiResponse(code = 401, message = "UNAUTHORIZED", response = ExceptionResponse.class),
            @ApiResponse(code = 403, message = "FORBIDDEN", response = ExceptionResponse.class),
            @ApiResponse(code = 404, message = "NOT FOUND", response = ExceptionResponse.class),
    })
    @PostMapping
    public ResponseEntity<COrder> saveOrder(@Valid @RequestBody COrder order){
        return orderService.saveOrder(order);
    }

    @ApiOperation(value = "Fetch Order By Id", response = COrder.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS", response = COrder.class),
            @ApiResponse(code = 404, message = "NOT FOUND", response = ExceptionResponse.class)
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<COrder> getOrder(@PathVariable int id){
        return orderService.getOrder(id);
    }

    @ApiOperation(value = "Fetch All Orders", response = COrder.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS", response = COrder.class),
            @ApiResponse(code = 404, message = "NOT FOUND", response = ExceptionResponse.class)
    })
    @GetMapping
    public List getAllOrders(){
        return  orderService.getAllOrders();
    }

    @ApiOperation(value = "Delete an Order By Id", response = COrder.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS", response = COrder.class),
            @ApiResponse(code = 404, message = "NOT FOUND", response = ExceptionResponse.class)
    })
    @DeleteMapping(value = "/{id}")
    public String deleteOrder(@PathVariable int id ){
        return orderService.deleteOrder(id);
    }

    @ApiOperation(value = "Update an Order", response = COrder.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS", response = COrder.class),
            @ApiResponse(code = 404, message = "NOT FOUND", response = ExceptionResponse.class)
    })
    @PutMapping
    public String updateOrder(@RequestBody @Valid COrder order){
        return orderService.updateOrder(order);
    }
}

