package todo.API.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import todo.API.Entityes.CasesEntity;
import todo.API.repository.CasesRepo;

import java.util.UUID;

@Service
public class CasesServiceImpl implements CasesService{

    @Autowired
    private CasesRepo casesRepo;

    // Создаёт новый список
    public void create(CasesEntity casesEntity) { casesRepo.save(casesEntity); };

    // Возвращает список всех списков
    public Page<CasesEntity> readAll(Pageable pageable) { return casesRepo.findAll(pageable); };

    // Возвращает список по его ID
    public CasesEntity read(UUID id) { return casesRepo.getOne(id); };

    // Обновляет список по заданному ID
    // true - данные обновлены, иначе false
    public boolean update(CasesEntity casesEntity, UUID id) {
        if (casesRepo.existsById(id)) {
            casesEntity.setId(id);
            casesRepo.save(casesEntity);
            return true;
        }

        return false;
    }

    //Удаляет список по заданному ID
    // true - список удалён, иначе false
    public boolean delete(UUID id) {
        if (casesRepo.existsById(id)) {
            casesRepo.deleteById(id);
            return true;
        }
        return false;
    }
}
