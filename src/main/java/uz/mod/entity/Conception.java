package uz.mod.entity;

import com.fasterxml.jackson.annotation.*;
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
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Conception extends AbstractEntity {


    private String conceptName;


    private String conceptionNameRu;


    public Conception(String conceptName, String conceptionNameRu) {
        this.conceptName = conceptName;
        this.conceptionNameRu = conceptionNameRu;
    }

}
