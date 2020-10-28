package todo.API.controllers;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import todo.API.entities.ListsEntity;
import todo.API.service.impl.ListsServiceImpl;
import todo.API.exceptions.BigLengthException;
import todo.API.exceptions.ConflictException;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/** Контроллер операций над списками */
@Api(description = "Операции над списками")
@RestController
@RequestMapping("lists")
public class ListsController {

    private final ListsServiceImpl listsServiceImpl;

    @Autowired
    private ListsController(ListsServiceImpl listsServiceImpl) {
        this.listsServiceImpl = listsServiceImpl;
    }


    /** Добавление нового списка дел
     * На вход подаётся только заголовок */
    @ApiOperation(value = "Создать лист", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "CREATED"),
            @ApiResponse(code = 409, message = "CONFLICT")
    })
    @PostMapping(produces = "application/json")
    public ResponseEntity<?> create(@RequestParam(name = "title") String title) throws BigLengthException, ConflictException {
        if(title.length() > 250) {
            throw new BigLengthException();
        }
        if(listsServiceImpl.readByTitle(title) != null) {
            throw new ConflictException();
        }
        ListsEntity listsEntity = new ListsEntity();
        listsEntity.setTitle(title);
        listsEntity.setCreateDate(LocalDateTime.now());
        listsEntity.setChangeDate(LocalDateTime.now());
        listsServiceImpl.create(listsEntity);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /** Вывод списков дел с пагинацией и сортировкой */
    @ApiOperation(value = "Вывод списков дел с пагинацией и сортировкой")
    @ApiResponse(code = 404, message = "NOT FOUND")
    @GetMapping(produces = "application/json")
    public ResponseEntity<?> read(
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
                : new ResponseEntity<String>("Lists not found",HttpStatus.NOT_FOUND);
    }

    /** Поиск списка по его ID */
    @ApiOperation(value = "Поиск списка по его ID")
    @ApiResponse(code = 404, message = "NOT FOUND")
    @GetMapping(value = "{id}", produces = "application/json")
    public ResponseEntity<?> read(@PathVariable(name = "id") UUID id) {
        ListsEntity listsEntity = listsServiceImpl.read(id);

        return listsEntity != null
                ? new ResponseEntity<>(listsEntity, HttpStatus.OK)
                : new ResponseEntity<String>("Lists not found", HttpStatus.NOT_FOUND);
    }

    /** Изменение заголовка списка по его ID */
    @ApiOperation(value = "Изменение заголовка списка по его ID")
    @ApiResponse(code = 304, message = "NOT MODIFIED")
    @PutMapping(value = "{id}", produces = "application/json")
    public ResponseEntity<?> update(@PathVariable(name = "id") UUID id,
                                    @RequestParam String title
    ) throws BigLengthException {
        if(title.length() > 250) {
            throw new BigLengthException();
        }
        ListsEntity listsEntity = listsServiceImpl.read(id);
        listsEntity.setTitle(title);
        listsEntity.setChangeDate(LocalDateTime.now());
        final boolean updated = listsServiceImpl.update(listsEntity, id);

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<String>("Not modified", HttpStatus.NOT_MODIFIED);
    }

    /** Удаление списка по его ID */
    @ApiOperation(value = "Удаление списка по его ID")
    @ApiResponse(code = 404, message = "NOT FOUND")
    @DeleteMapping(value = "{id}", produces = "application/json")
    public ResponseEntity<?> delete(@PathVariable(name = "id", required = true) UUID id) {
        final boolean deleted = listsServiceImpl.delete(id);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<String>("Not deleted", HttpStatus.NOT_FOUND);
    }


}
