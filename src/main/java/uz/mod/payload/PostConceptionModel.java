package uz.mod.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.mod.entity.PostConception;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostConceptionModel {

    private UUID id;

    private String fullName;

    private UUID districtId;

    private UUID regionId;

    private String schoolName;

    private String description;


    private UUID detailId;

    public PostConceptionModel(PostConception postConception) {
        this.id = postConception.getId();
        this.fullName = postConception.getFullName();
        this.districtId = postConception.getDistrict().getId();
        this.regionId = postConception.getRegion().getId();
        this.schoolName = postConception.getSchoolName();
        this.description = postConception.getDescription();
        this.detailId = postConception.getDetails().getId();
    }
}
