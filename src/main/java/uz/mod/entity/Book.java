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
public class Book extends AbstractEntity {

    @NotNull
    private String nameUz;

    private String nameRu;

    @JsonIgnore
    @OneToMany(mappedBy = "book")
    private List<PostBook> postBooks;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToOne(fetch = FetchType.EAGER)
    private Image image;

    @OneToOne(fetch = FetchType.EAGER)
    private Pdf pdf;
}
