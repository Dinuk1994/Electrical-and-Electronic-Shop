package dto.tm;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderDetailsTm extends RecursiveTreeObject<OrderDetailsTm> {
    private String orderId;
    private String itemCode;
    private String customerName;
    private String itemCategory;
    private String itemName;
    private int itemQty;
    private double itemPrice;
    private String status;
}