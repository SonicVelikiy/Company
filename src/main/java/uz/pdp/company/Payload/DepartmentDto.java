package uz.pdp.company.Payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDto {

    @NotNull(message = "name bo'sh bo'lmasligi kerakk")
    private String name;

    @NotNull(message = "company bo'sh bo'lmasligi kerak")
    private Integer company;
}
