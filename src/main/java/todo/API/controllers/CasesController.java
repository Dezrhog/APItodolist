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
import todo.API.Entityes.CasesEntity;
import todo.API.Entityes.ListsEntity;
import todo.API.service.CasesServiceImpl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("lists/{id}/cases")
public class CasesController {

    private final CasesServiceImpl casesService;

    @Autowired
    private CasesController(CasesServiceImpl casesService) { this.casesService = casesService; }

    @GetMapping   // Обработка GET на адрес /lists с пагинацией и сортировкой
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

    @GetMapping("{caseId}")
    public ResponseEntity<CasesEntity> getCase(@PathVariable("caseId") UUID id) {
        CasesEntity casesEntity = casesService.read(id);

        return casesEntity != null
                ? new ResponseEntity<>(casesEntity, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> create(@PathVariable("id") ListsEntity listsEntity,
                              @RequestBody CasesEntity casesEntity) {
        casesEntity.setCaseCreationDate(LocalDateTime.now());
        casesEntity.setCaseChangeDate(LocalDateTime.now());
        casesEntity.setList(listsEntity);
        if (casesEntity.getComplete() == null)
            casesEntity.setComplete(false);
        casesService.create(casesEntity);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("{caseId}")
    public ResponseEntity<?> update(@PathVariable("caseId") UUID id,
                                    @RequestBody CasesEntity casesEntity) {
        casesEntity.setCaseChangeDate(LocalDateTime.now());

        final boolean updated = casesService.update(casesEntity, id);       // Обновляем объект в БД

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PutMapping("/markDone/{caseId}")
    public ResponseEntity<?> markDone(@PathVariable("caseId") UUID id) {
        CasesEntity casesEntity = casesService.read(id);
        casesEntity.setComplete(true);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("caseId")
    public ResponseEntity<?> delete(@PathVariable(name = "caseId") UUID id) {
        final boolean deleted = casesService.delete(id);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
