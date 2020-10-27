package todo.API.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import todo.API.entities.ListsEntity;

import java.util.UUID;

@Repository
public interface ListsRepo extends JpaRepository<ListsEntity, UUID> {

    Page<ListsEntity> findAll(Pageable pageable);

    Page<ListsEntity> findByTitleContaining(Pageable pageable, String title);

    ListsEntity findByTitle(String title);
}