package todo.API.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import todo.API.Entityes.ListsEntity;
import todo.API.service.ListsServiceImpl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("lists")
public class ListsController {

    private final ListsServiceImpl listsServiceImpl;

    @Autowired
    private ListsController(ListsServiceImpl listsServiceImpl) {
        this.listsServiceImpl = listsServiceImpl;
    }

    @PostMapping   // Создание нового списка дел
    public ResponseEntity<?> create(@RequestParam(name = "title") String title) {
        ListsEntity listsEntity = new ListsEntity();
        listsEntity.setTitle(title);
        listsEntity.setCreateDate(LocalDateTime.now());
        listsEntity.setChangeDate(LocalDateTime.now());
        listsServiceImpl.create(listsEntity);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping   // Вывод списка списков дел с пагинацией и сортировкой
    public ResponseEntity<Page<ListsEntity>> read(
            @RequestParam(name = "page") Optional<Integer> page,
            @RequestParam(name = "title", required = false) String title,
            @SortDefault(sort="id", direction = Sort.Direction.ASC) Sort sort
    ) {
        Pageable pageable = PageRequest.of(page.orElse(0), 10, sort);
        Page<ListsEntity> listsEntityPage;
        if (title == null)
            listsEntityPage = listsServiceImpl.readAll(pageable);
        else
            listsEntityPage = listsServiceImpl.readByTitle(pageable, title);

        return listsEntityPage != null && !listsEntityPage.isEmpty()
                ? new ResponseEntity<>(listsEntityPage, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("{id}") // Поиск списка по ID
    public ResponseEntity<ListsEntity> read(@PathVariable(name = "id") UUID id) {
        ListsEntity listsEntity = listsServiceImpl.read(id);

        return listsEntity != null
                ? new ResponseEntity<>(listsEntity, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("{id}") // Инзменение заголовка (названия) списка по его ID
    public ResponseEntity<?> update(@PathVariable(name = "id") UUID id,
                                    @RequestParam String title
    ) {
        ListsEntity listsEntity = listsServiceImpl.read(id);
        listsEntity.setTitle(title);
        listsEntity.setChangeDate(LocalDateTime.now());                             // Обновляем дату изменения
        final boolean updated = listsServiceImpl.update(listsEntity, id);       // Обновляем объект в БД

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping("{id}")  // Удаление списка по его ID
    public ResponseEntity<?> delete(@PathVariable(name = "id") UUID id) {
        final boolean deleted = listsServiceImpl.delete(id);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
