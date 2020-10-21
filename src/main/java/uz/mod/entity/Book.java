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
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Book extends AbstractEntity {

    @NotNull
    private String nameUz;

    private String nameRu;

    private Boolean isFavourite;

    @JsonIgnore
    @OneToMany(mappedBy = "book")
    private List<PostBook> postBooks;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToOne
    private Image image;

    @OneToOne
    private Pdf pdf;
}
