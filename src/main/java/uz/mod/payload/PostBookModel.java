package uz.mod.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.mod.entity.PostBook;
import uz.mod.entity.PostConception;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostBookModel {

    private UUID id;

    private String fullName;

    private UUID districtId;

    private UUID regionId;

    private String schoolName;

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
