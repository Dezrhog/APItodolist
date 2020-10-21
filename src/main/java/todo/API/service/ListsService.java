package todo.API.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import todo.API.Entityes.ListsEntity;

public interface ListsService {

    // Создаёт новый список
    void create(ListsEntity listsEntity);

    // Возвращает список всех списков
    Page<ListsEntity> readAll(Pageable pageable);

    // Возвращает список по его ID
    ListsEntity read(long id);

    // Обновляет список по заданному ID
    // true - данные обновлены, иначе false
    boolean update(ListsEntity listsEntity, long id);

    //Удаляет список по заданному ID
    // true - список удалён, иначе false
    boolean delete(long id);
}
