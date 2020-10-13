package uz.mod.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.mod.entity.abstractEntityLayer.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Subject extends AbstractEntity {


    private String subjectName;


    private String subjectNameRu;


    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("categoriesId")
    @ManyToOne(fetch = FetchType.EAGER)
    private Category categories;

    public Subject(String subjectName, String subjectNameRu, Category categories) {
        this.subjectName = subjectName;
        this.subjectNameRu = subjectNameRu;
        this.categories = categories;
    }
}
