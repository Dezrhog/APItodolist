package todo.API.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import todo.API.Entityes.CasesEntity;
import todo.API.Entityes.ListsEntity;

import javax.transaction.Transactional;
import java.util.UUID;

public interface CasesRepo extends JpaRepository<CasesEntity, UUID> {

    Page<CasesEntity> findAll(Pageable pageable);

    Page<CasesEntity> findByList(ListsEntity list, Pageable pageable);

    @Transactional
    void deleteAllByList_Id(UUID id);

    Page<CasesEntity> findByListAndNameContaining (ListsEntity listsEntity, Pageable pageable, String name);
}
