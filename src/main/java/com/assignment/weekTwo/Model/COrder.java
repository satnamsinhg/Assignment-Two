package com.assignment.weekTwo.Model;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Data
@Table(name = "corder")
public class COrder {

    @ApiModelProperty(name = "id", required = true, value = "4", notes = "ID of order")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Column(name = "orderid")
    private int orderId;

    @ApiModelProperty(name = "orderStatus", required = true, value = "PENDING", notes = "Status of order")
    @Column(name = "orderstatus")
    @Enumerated(value = EnumType.STRING)
    private Status orderStatus;

//    @ApiModelProperty(name = "orderDate", required = false, notes = "Date of order")
//    @Column(name = "orderdate")
//    @CreationTimestamp
//    private java.sql.Timestamp orderDate;

    @ApiModelProperty(name = "customerName", required = true, notes = "Name of Customer")
    @Column(name = "customername")
    @Size(min = 3, message = "Customer name should be atleast 3 characters long")
    private String customerName;

    @ApiModelProperty(name = "customerAddress", required = true, notes = "Address of Customer")
    @Column(name = "customeraddress")
    @Size(min = 3, message = "Customer Address should be atleast 3 characters long")
    private String customerAddress;

    @ApiModelProperty(name = "orderDetails", required = true, notes = "Details of Order")
    @Column(name = "orderdetails")
    @NotBlank(message = "Order details should not be null")
    private String orderDetails;

    @ApiModelProperty(name = "orderBill", required = true, notes = "Total Charges of Order")
    @Column(name = "orderbill")
    @NotBlank(message = "Please provide order bill details")
    private String orderBill;

    public enum Status{
        PENDING, SHIPPED, CANCELLED, COMPLETED;
    }
}
