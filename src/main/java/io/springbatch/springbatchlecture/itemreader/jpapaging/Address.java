package io.springbatch.springbatchlecture.itemreader.jpapaging;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Address {

    @Id
    @GeneratedValue
    private Long id;
    private String location;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
