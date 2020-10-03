package uz.mod.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.mod.entity.Conception;
import uz.mod.entity.District;
import uz.mod.entity.PostConception;
import uz.mod.entity.Region;

import javax.persistence.ManyToOne;
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

    private Boolean isEnabled;

    private String description;

    private Boolean isFavourite;

    private UUID conceptionId;

    public PostConceptionModel(PostConception postConception) {
        this.id = postConception.getId();
        this.fullName = postConception.getFullName();
        this.districtId = postConception.getDistrict().getId();
        this.regionId = postConception.getRegion().getId();
        this.schoolName = postConception.getSchoolName();
        this.isEnabled = postConception.getIsEnabled();
        this.description = postConception.getDescription();
        this.isFavourite = postConception.getIsFavourite();
        this.conceptionId = postConception.getConception().getId();
    }
}
