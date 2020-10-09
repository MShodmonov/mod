package uz.mod.entity;


import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.mod.entity.abstractEntityLayer.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PostBook extends AbstractEntity {

    private String fullName;

    @ManyToOne
    private District district;

    @ManyToOne
    private Region region;

    private String schoolName;

    private Boolean isEnabled;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Boolean isFavourite;


    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("bookId")
    @ManyToOne(optional = false)
    private Book book;



}
