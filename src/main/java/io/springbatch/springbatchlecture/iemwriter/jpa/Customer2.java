package io.springbatch.springbatchlecture.iemwriter.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Customer2 {

    @Id
    private long id;
    private String firstName;
    private String lastName;
    private Date birthdate;
}
