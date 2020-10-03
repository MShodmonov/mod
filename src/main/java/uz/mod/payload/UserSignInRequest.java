package uz.mod.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSignInRequest {


    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
