package uz.mod.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.mod.entity.Book;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {

    private Boolean isSuccess;
}
