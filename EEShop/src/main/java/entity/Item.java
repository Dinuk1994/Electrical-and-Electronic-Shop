package entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;



@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity

public class Item {
    @Id
    private String itemCode;
    private String itemCategory;
    private String itemName;
    private double itemPrice;

    @OneToMany(mappedBy = "item")
    private List<OrderDetails> orderDetails=new ArrayList<>();

    public Item(String itemCode, String itemCategory, String itemName, double itemPrice) {
        this.itemCode = itemCode;
        this.itemCategory = itemCategory;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
    }
}
