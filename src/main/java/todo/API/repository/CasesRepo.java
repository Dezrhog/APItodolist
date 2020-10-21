package todo.API.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import todo.API.Entityes.CasesEntity;
import todo.API.Entityes.ListsEntity;

import java.util.UUID;

public interface CasesRepo extends JpaRepository<CasesEntity, UUID> {

    Page<CasesEntity> findAll(Pageable pageable);
}
