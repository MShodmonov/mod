package uz.mod.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardInfo {

    private Long correspondentCount;

    private Long bookCount;

    private Long bookPostCount;

    private Long postConceptionCount;

    private String bookName;


}
