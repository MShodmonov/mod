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


    private String conceptName;


    private String conceptionNameRu;


    @Column(columnDefinition = "TEXT")
    private String description;



    @ManyToOne
    private Subject subject;


    @ManyToOne
    private Category category;

    public Conception(String conceptName,String conceptionNameRu, String description, Category category, Subject subject) {
        this.conceptName = conceptName;
        this.conceptionNameRu = conceptionNameRu;
        this.description = description;
        this.category = category;
        this.subject = subject;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "conception")
    private List<PostConception> postConceptionList;
}
