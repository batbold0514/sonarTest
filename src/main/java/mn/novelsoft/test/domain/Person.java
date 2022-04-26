package mn.novelsoft.test.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Person {

    private String firstname;
    private String lastname;
    private Date birthdate;
    private String gender;
}
