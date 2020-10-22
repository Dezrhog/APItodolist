package todo.API.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import todo.API.Entityes.ListsEntity;

import java.util.UUID;

public interface ListsService {

    // Создаёт новый список
    void create(ListsEntity listsEntity);

    // Возвращает список всех списков
    Page<ListsEntity> readAll(Pageable pageable);

    // Возвращает списки по заголовку или его части
    Page<ListsEntity> readByTitle(Pageable pageable, String title);

    // Возвращает список по его ID
    ListsEntity read(UUID id);

    // Обновляет список по заданному ID
    // true - данные обновлены, иначе false
    boolean update(ListsEntity listsEntity, UUID id);

    //Удаляет список по заданному ID
    // true - список удалён, иначе false
    boolean delete(UUID id);
}
