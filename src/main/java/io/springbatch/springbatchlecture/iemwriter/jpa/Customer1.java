package io.springbatch.springbatchlecture.iemwriter.jpa;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class Customer1 {

    private final long id;
    private final String firstName;
    private final String lastName;
    private final Date birthdate;
}
