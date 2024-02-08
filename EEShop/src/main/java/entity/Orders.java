package entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class Orders {
    @Id
    private String orderId;
    private String itemCategory;
    private  String customerId;
    private String itemName;
    private int itemQty;
    private double itemPrice;
    private String orderDate;

    @ManyToOne()
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "orders")
    private List<OrderDetails> orderDetails=new ArrayList<>();

    public Orders(String orderId, String itemCategory,String customerId, String itemName, int itemQty, double itemPrice, String orderDate) {
        this.orderId = orderId;
        this.itemCategory = itemCategory;
        this.customerId=customerId;
        this.itemName = itemName;
        this.itemQty = itemQty;
        this.itemPrice = itemPrice;
        this.orderDate = orderDate;


    }
}

