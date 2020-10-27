package todo.API.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Data
@Table(name = "lists")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ListsEntity {
    @Id
    @ApiModelProperty(notes = "The database generated list ID")
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private UUID id;

    @ApiModelProperty(notes = "The auto-generated date of change of list")
    @Column(name = "changedate", nullable = false)
    private LocalDateTime changeDate;

    @ApiModelProperty(notes = "The auto-generated creation date of the list")
    @Column(name = "createdate", nullable = false)
    private LocalDateTime createDate;

    @ApiModelProperty(notes = "The title of the list")
    @Size(max = 250, message = "Title can't be so long")
    @Column(name = "title", unique = true, nullable = false)
    private String title;
}
