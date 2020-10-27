package todo.API.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import todo.API.entities.CasesEntity;
import todo.API.entities.ListsEntity;

import java.util.UUID;

public interface CasesService {
    // Создаёт новое дело
    void create(CasesEntity casesEntity);

    // Возвращает дело по его ID
    CasesEntity read(UUID id);

    CasesEntity readByListAndId(ListsEntity listsEntity, UUID id);

    // Обновляет дело по заданному ID
    // true - данные обновлены, иначе false
    boolean update(CasesEntity listsEntity, UUID id);

    //Удаляет дело по заданному ID
    // true - дело удалено, иначе false
    boolean delete(UUID id);

    Page<CasesEntity> readByListAndName(ListsEntity listsEntity, Pageable pageable, String name);

    Page<CasesEntity> readByList(ListsEntity listsEntity, Pageable pageable);

    boolean readByName(String name);

    boolean readById(UUID id);
}
