package todo.API.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import todo.API.Entityes.ListsEntity;
import todo.API.service.ListsServiceImpl;

import java.time.LocalDate;
import java.util.Optional;

@RestController
public class ListsController {

    private final ListsServiceImpl listsServiceImpl;

    @Autowired
    private ListsController(ListsServiceImpl listsServiceImpl) {
        this.listsServiceImpl = listsServiceImpl;
    }

    @PostMapping(value = "/lists")   // Обработка POST на адрес /lists
    public ResponseEntity<?> create(@RequestParam(name = "title") String title) {
        ListsEntity listsEntity = new ListsEntity();
        listsEntity.setTitle(title);
        listsEntity.setCreateDate(LocalDate.now());
        listsEntity.setChangeDate(LocalDate.now());
        listsServiceImpl.create(listsEntity);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/lists")   // Обработка GET на адрес /lists
    public ResponseEntity<Page<ListsEntity>> read(
            @RequestParam(name = "page") Optional<Integer> page
    ) {
        Pageable pageable = PageRequest.of(page.orElse(0), 10);
        Page<ListsEntity> listsEntityPage = listsServiceImpl.readAll(pageable);

        return listsEntityPage != null && !listsEntityPage.isEmpty()
                ? new ResponseEntity<>(listsEntityPage, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/lists/{id}")
    public ResponseEntity<ListsEntity> read(@PathVariable(name = "id") int id) {
        ListsEntity listsEntity = listsServiceImpl.read(id);

        return listsEntity != null
                ? new ResponseEntity<>(listsEntity, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/lists/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") int id, @RequestBody ListsEntity listsEntity) {
        final boolean updated = listsServiceImpl.update(listsEntity, id);

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/lists/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        final boolean deleted = listsServiceImpl.delete(id);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
