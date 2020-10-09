package uz.mod.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.mod.entity.abstractEntityLayer.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class District extends AbstractEntity {

    private String nameUz;

    private String nameOz;

    private String nameRu;



    @JsonIgnore
    @ManyToOne
    private Region region;

    public District(String nameUz ,String nameOz, String nameRu) {
        this.nameUz = nameUz;
        this.nameOz = nameOz;
        this.nameRu = nameRu;
    }
}
