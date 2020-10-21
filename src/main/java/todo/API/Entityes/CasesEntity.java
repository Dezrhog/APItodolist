package todo.API.Entityes;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "cases", schema = "public", catalog = "postgres")
public class CasesEntity {
    private int id;
    private Date caseChangeDate;
    private Date caseCreationDate;
    private Boolean complete;
    private String description;
    private String name;
    private short urgency;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "casechangedate")
    public Date getCaseChangeDate() {
        return caseChangeDate;
    }

    public void setCaseChangeDate(Date caseChangeDate) {
        this.caseChangeDate = caseChangeDate;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "casecreationdate")
    public Date getCaseCreationDate() {
        return caseCreationDate;
    }

    public void setCaseCreationDate(Date caseCreationDate) {
        this.caseCreationDate = caseCreationDate;
    }

    @Basic
    @Column(name = "complete")
    public Boolean getComplete() {
        return complete;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "urgency", nullable = false)
    public short getUrgency() {
        return urgency;
    }

    public void setUrgency(short urgency) {
        this.urgency = urgency;
    }

    private ListsEntity listsEntity;

    @ManyToOne
    @JoinColumn(name = "cases_fk", referencedColumnName = "id")
    public ListsEntity getListsEntity() {
        return this.listsEntity;
    }

    public void setListsEntity(ListsEntity lists) {
        this.listsEntity = lists;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CasesEntity that = (CasesEntity) o;
        return id == that.id &&
                urgency == that.urgency &&
                Objects.equals(caseChangeDate, that.caseChangeDate) &&
                Objects.equals(caseCreationDate, that.caseCreationDate) &&
                Objects.equals(complete, that.complete) &&
                Objects.equals(description, that.description) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, caseChangeDate, caseCreationDate, complete, description, name, urgency);
    }
}
