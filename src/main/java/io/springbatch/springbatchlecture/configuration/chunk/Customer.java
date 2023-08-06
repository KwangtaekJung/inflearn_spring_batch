package io.springbatch.springbatchlecture.configuration.chunk;

import lombok.Data;

@Data
public class Customer {

    private String name;

    public Customer(String name) {
        this.name = name;
    }
}
