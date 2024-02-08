package dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OrdersDto {
    private String orderId;
    private String itemCategory;
    private String customerId;
    private String itemName;
    private int itemQty;
    private double itemPrice;
    private String orderDate;
    private List<OrderDetailsDto> list;
}
