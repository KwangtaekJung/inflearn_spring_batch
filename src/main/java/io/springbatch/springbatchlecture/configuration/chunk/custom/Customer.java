package io.springbatch.springbatchlecture.configuration.chunk.custom;

import lombok.Data;

@Data
public class Customer {

    private String name;

    public Customer(String name) {
        this.name = name;
    }
}
