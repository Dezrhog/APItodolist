package todo.API.Entityes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "cases")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CasesEntity {
    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private UUID id;

    @Column(name = "casechangedate")
    private LocalDateTime caseChangeDate;

    @Column(name = "casecreationdate")
    private LocalDateTime caseCreationDate;

    @Basic
    @Column(name = "complete")
    private Boolean complete;

    @Basic
    @Column(name = "description")
    private String description;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "urgency", nullable = false)
    private short urgency;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lists_id", referencedColumnName = "id")
    private ListsEntity list;
}
