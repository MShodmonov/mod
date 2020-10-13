package uz.mod.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.mod.entity.PostResource;
import uz.mod.entity.Region;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResourcePayload {
    private String fullName;

    private UUID districtId;

    private UUID regionId;

    private String schoolName;

    private String description;

    private UUID fileId;

    public PostResourcePayload(PostResource postResource) {
        this.fullName = postResource.getFullName();
        this.districtId = postResource.getDistrict().getId();
        this.regionId = postResource.getRegion().getId();
        this.schoolName = postResource.getSchoolName();
        this.description = postResource.getDescription();
        this.fileId = postResource.getFile().getId();
    }
}
