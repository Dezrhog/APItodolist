package todo.API.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import todo.API.Entityes.CasesEntity;

import java.util.UUID;

public interface CasesService {
    // Создаёт новый список
    void create(CasesEntity casesEntity);

    // Возвращает список всех списков
    Page<CasesEntity> readAll(Pageable pageable);

    // Возвращает список по его ID
    CasesEntity read(UUID id);

    // Обновляет список по заданному ID
    // true - данные обновлены, иначе false
    boolean update(CasesEntity listsEntity, UUID id);

    //Удаляет список по заданному ID
    // true - список удалён, иначе false
    boolean delete(UUID id);
}
