package todo.API.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import todo.API.entities.CasesEntity;
import todo.API.entities.ListsEntity;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

public interface CasesRepo extends JpaRepository<CasesEntity, UUID> {

    Page<CasesEntity> findAll(Pageable pageable);

    Page<CasesEntity> findByList(ListsEntity list, Pageable pageable);

    CasesEntity findByListAndId(ListsEntity list, UUID id);

    CasesEntity findByName(String name);

    @Transactional
    void deleteAllByList_Id(UUID id);

    Page<CasesEntity> findByListAndNameContaining (ListsEntity listsEntity, Pageable pageable, String name);

    Optional<CasesEntity> findById (UUID id);
}
