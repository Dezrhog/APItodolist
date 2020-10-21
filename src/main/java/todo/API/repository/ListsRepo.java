package todo.API.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import todo.API.Entityes.ListsEntity;

@Repository
public interface ListsRepo extends JpaRepository<ListsEntity, Long> {

    Page<ListsEntity> findAll(Pageable pageable);
}
