package org.youngmonkeys.personal.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

import static org.youngmonkeys.personal.constant.PersonalConstants.TABLE_NAME_COIN_PRICE;

@Getter
@Setter
@ToString
@Entity
@Table(name = TABLE_NAME_COIN_PRICE)
@AllArgsConstructor
@NoArgsConstructor
public class PersonalCoinPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String symbol;

    private String name;

    private String price;

    @Column(name = "price_change")
    private String priceChange;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
