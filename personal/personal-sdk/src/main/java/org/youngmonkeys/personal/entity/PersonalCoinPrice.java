package org.youngmonkeys.personal.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
    @Column(name = "id")
    private long id;

    @Column(name = "symbol")
    private String symbol;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private String price;

    @Column(name = "price_change")
    private String priceChange;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
