package uz.mod.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.mod.entity.PostBook;
import uz.mod.entity.PostConception;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostBookModel {

    private UUID id;

    private String fullName;

    @NotNull
    private UUID districtId;

    @NotNull
    private UUID regionId;

    private String schoolName;

    @NotNull
    private String description;

    private UUID bookId;

    public PostBookModel(PostBook postBook) {
        this.id = postBook.getId();
        this.fullName = postBook.getFullName();
        this.districtId = postBook.getDistrict().getId();
        this.regionId = postBook.getRegion().getId();
        this.schoolName = postBook.getSchoolName();
        this.description = postBook.getDescription();
        this.bookId = postBook.getBook().getId();
    }
}
