package uz.mod.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import uz.mod.entity.abstractEntityLayer.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Conception extends AbstractEntity {

    @NotNull
    private String conceptName;


    @ManyToMany(mappedBy = "conceptionList",cascade = { CascadeType.ALL })
    private List<Subject> subjects;

    @ManyToOne
    private Category category;

    public Conception(String conceptName, Category category) {
        this.conceptName = conceptName;
        this.category = category;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "conception")
    private List<PostConception> postConceptionList;
}