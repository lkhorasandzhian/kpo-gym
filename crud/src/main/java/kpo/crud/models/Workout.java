package kpo.crud.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "Workout")
@AllArgsConstructor
@NoArgsConstructor
public class Workout {
    @Id
    @Column(name = "id", insertable = false)
    @GeneratedValue
    private Integer id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private Integer time;
    @Column
    private String coach;

}
