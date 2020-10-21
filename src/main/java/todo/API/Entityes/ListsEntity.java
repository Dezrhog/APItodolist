package todo.API.Entityes;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "lists")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ListsEntity {
    private long id;
    private LocalDate changeDate;
    private LocalDate createDate;
    private String title;

    @Id
    @SequenceGenerator(name = "listsIdSeq", sequenceName = "lists_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "listsIdSeq")
    @Column(name = "id", unique = true, nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "changedate")
    public LocalDate getChangeDate() {
        return this.changeDate;
    }

    public void setChangeDate(LocalDate changeDate) {
        this.changeDate = changeDate;
    }

    @Column(name = "createdate")
    public LocalDate getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    // One To Many
    private Set<CasesEntity> cases = new HashSet<>();

    @OneToMany(mappedBy = "listsEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<CasesEntity> getCasesEntities() {
        return this.cases;
    }

    public void setCasesEntities(Set<CasesEntity> casesEntities) {
        this.cases = casesEntities;
    }

    public void addCases(CasesEntity cases) {
        cases.setListsEntity(this);
        getCasesEntities().add(cases);
    }

    public void removeCases(CasesEntity cases) {
        getCasesEntities().remove(cases);
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListsEntity that = (ListsEntity) o;
        return id == that.id &&
                Objects.equals(changeDate, that.changeDate) &&
                Objects.equals(createDate, that.createDate) &&
                Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, changeDate, createDate, title);
    }
}
