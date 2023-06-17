package kpo.crud.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutRequest {
    private String name;
    private String description;
    private Integer time;
    private String coach;
}
