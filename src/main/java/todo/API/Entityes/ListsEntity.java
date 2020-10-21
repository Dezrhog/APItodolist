package todo.API.Entityes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Data
@Table(name = "lists")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ListsEntity {
    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private UUID id;

    @Column(name = "changedate")
    private LocalDateTime changeDate;

    @Column(name = "createdate")
    private LocalDateTime createDate;

    @Column(name = "title")
    private String title;
}
