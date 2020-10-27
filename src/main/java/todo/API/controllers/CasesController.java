package todo.API.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import todo.API.entities.CasesEntity;
import todo.API.entities.ListsEntity;
import todo.API.serviceimpl.CasesServiceImpl;
import todo.API.utils.BigLengthException;
import todo.API.utils.ConflictException;
import todo.API.utils.NotFoundException;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Api(description = "Operations with cases")
@RestController
@RequestMapping("lists/{id}/cases")
public class CasesController {

    private final CasesServiceImpl casesService;

    @Autowired
    private CasesController(CasesServiceImpl casesService) { this.casesService = casesService; }

    @ApiOperation(value = "Output to-do cases with pagination and sorting")
    @ApiResponse(code = 404, message = "NOT FOUND")
    @GetMapping(produces = "application/json")   // Обработка GET на адрес /lists с пагинацией и сортировкой
    public ResponseEntity<Page<CasesEntity>> read(
            @PathVariable("id") ListsEntity listsEntity,
            @RequestParam(name = "page") Optional<Integer> page,
            @RequestParam(name = "name", required = false) String name,
            @SortDefault(sort="id", direction = Sort.Direction.ASC) Sort sort
    ) {

        Pageable pageable = PageRequest.of(page.orElse(0), 10, sort);
        Page<CasesEntity> casesEntityPage;
        if(name != null)
            casesEntityPage = casesService.readByListAndName(listsEntity, pageable, name);
        else
            casesEntityPage = casesService.readByList(listsEntity, pageable);

        return casesEntityPage != null && !casesEntityPage.isEmpty()
                ? new ResponseEntity<>(casesEntityPage, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "Find case by ID")
    @ApiResponse(code = 404, message = "NOT FOUND")
    @GetMapping(value = "{caseId}", produces = "application/json")
    public ResponseEntity<CasesEntity> getCase(@PathVariable("id") ListsEntity listsEntity,
            @PathVariable("caseId") UUID id) {
        CasesEntity casesEntity = casesService.readByListAndId(listsEntity, id);

        return casesEntity != null
                ? new ResponseEntity<>(casesEntity, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "Create a case")
    @ApiResponse(code = 201, message = "CREATED")
    @PostMapping(produces = "application/json")
    public ResponseEntity<?> create(@PathVariable("id") ListsEntity listsEntity,
                              @RequestBody CasesEntity casesEntity) throws BigLengthException, ConflictException {

        if(casesEntity.getName().length() > 250 || casesEntity.getDescription().length() > 250)
            throw new BigLengthException();
        if(casesService.readByName(casesEntity.getName()))
            throw new ConflictException();

        casesEntity.setCaseCreationDate(LocalDateTime.now());
        casesEntity.setCaseChangeDate(LocalDateTime.now());
        casesEntity.setList(listsEntity);
        if (casesEntity.getComplete() == null)
            casesEntity.setComplete(false);

        casesService.create(casesEntity);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update the case by its ID")
    @ApiResponse(code = 304, message = "NOT MODIFIED")
    @PutMapping(value = "{caseId}", produces = "application/json")
    public ResponseEntity<?> update(@PathVariable("caseId") UUID id,
                                    @RequestBody CasesEntity casesEntity) {

        casesEntity.setCaseChangeDate(LocalDateTime.now());
        final boolean updated = casesService.update(casesEntity, id);
        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @ApiOperation(value = "Update the completeness of the case")
    @PutMapping(value = "/markDone/{caseId}", produces = "application/json")
    public ResponseEntity<?> markDone(@PathVariable("caseId") UUID id) throws NotFoundException{

        if(casesService.readById(id)) {
            throw new NotFoundException();
        }

        CasesEntity casesEntity = casesService.read(id);
        casesService.update(casesEntity, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Delete case by its ID")
    @ApiResponse(code = 304, message = "NOT MODIFIED")
    @DeleteMapping(value = "{caseId}", produces = "application/json")
    public ResponseEntity<?> delete(@PathVariable(name = "caseId") UUID id) {
        final boolean deleted = casesService.delete(id);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<String>("Not deleted", HttpStatus.NOT_MODIFIED);
    }
}
