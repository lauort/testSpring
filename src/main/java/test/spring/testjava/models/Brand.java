package test.spring.testjava.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "BRANDS")
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotNull(message = "Null name not allowed")
    @NotBlank(message = "Blank or empty name not allowed")
    private String name;

    @OneToMany(mappedBy="brand")
    private Set<Price> priceSet;

    @Builder
    public Brand(String name) {
        this.name = name;
    }

}


