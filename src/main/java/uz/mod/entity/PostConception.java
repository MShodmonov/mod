package uz.mod.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.mod.entity.abstractEntityLayer.AbstractEntity;
import uz.mod.payload.PostConceptionModel;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PostConception extends AbstractEntity {

    private String fullName;

    @ManyToOne
    private District district;

    @ManyToOne
    private Region region;

    private String schoolName;

    private Boolean isEnabled;

    private String description;

    private Boolean isFavourite;


    @ManyToOne(optional = true,fetch = FetchType.EAGER)
    private Conception conception;


}
