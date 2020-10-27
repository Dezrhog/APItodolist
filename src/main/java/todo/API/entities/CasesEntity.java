package todo.API.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "cases")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CasesEntity {
    @Id
    @ApiModelProperty(notes = "The database generated product ID")
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private UUID id;

    @ApiModelProperty(notes = "The auto-generated date of change of the case")
    @Column(name = "casechangedate", nullable = false)
    private LocalDateTime caseChangeDate;

    @ApiModelProperty(notes = "The auto-generated create date of the case")
    @Column(name = "casecreationdate", nullable = false)
    private LocalDateTime caseCreationDate;

    @ApiModelProperty(notes = "The completeness of the case")
    @Basic
    @Column(name = "complete", nullable = false)
    private Boolean complete;

    @ApiModelProperty(notes = "The description of the case")
    @Basic
    @Size(max = 250, message = "Description can't be so long")
    @Column(name = "description")
    private String description;

    @ApiModelProperty(notes = "The name of the case")
    @Basic
    @Size(max = 250, message = "Name can't be so long")
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @ApiModelProperty(notes = "The urgency of the case")
    @Basic
    @Column(name = "urgency", nullable = false)
    private short urgency;

    @ApiModelProperty(notes = "The link to the list that the case is attached to")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "lists_id", referencedColumnName = "id", nullable = false)
    private ListsEntity list;
}
