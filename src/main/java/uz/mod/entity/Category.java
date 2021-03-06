package uz.mod.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.mod.entity.abstractEntityLayer.AbstractEntity;

import javax.persistence.Entity;

import javax.persistence.OneToMany;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Category extends AbstractEntity {

    private String nameUz;
    private String nameRu;


    public Category(String nameUz, String nameRu) {
        this.nameRu = nameRu;
        this.nameUz = nameUz;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "categories")
    private List<Subject> subjects;



}
