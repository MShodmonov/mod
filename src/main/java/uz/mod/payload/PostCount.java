package uz.mod.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostCount {

    private String nameUz;

    private String nameRu;

    private Integer count;

    private UUID id;


}
