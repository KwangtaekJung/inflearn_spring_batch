package io.springbatch.springbatchlecture.iemwriter.jpa;

import org.modelmapper.ModelMapper;
import org.springframework.batch.item.ItemProcessor;

public class CustomitemProcessor implements ItemProcessor<Customer1, Customer2> {

    ModelMapper modelMapper = new ModelMapper();

    @Override
    public Customer2 process(Customer1 customer1) throws Exception {
        Customer2 customer2 = modelMapper.map(customer1, Customer2.class);
        return customer2;
    }
}
