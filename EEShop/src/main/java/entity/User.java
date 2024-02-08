package entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class User {
    @Id
    private String userId;
    private String address;
    private int contactNumber;
    private String email;
    private String name;
    private String primaryPassword;


}
