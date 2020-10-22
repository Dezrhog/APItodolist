package todo.API.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import todo.API.entities.CasesEntity;
import todo.API.entities.ListsEntity;
import todo.API.repository.CasesRepo;
import todo.API.service.CasesService;

import java.util.UUID;

@Service
public class CasesServiceImpl implements CasesService {

    @Autowired
    private CasesRepo casesRepo;

    @Override   // Создаёт новое дело
    public void create(CasesEntity casesEntity) { casesRepo.save(casesEntity); };

    @Override   // Возвращает дело по его ID
    public CasesEntity read(UUID id) { return casesRepo.getOne(id); };

                // Обновляет дело по заданному ID
    @Override   // true - данные обновлены, иначе false
    public boolean update(CasesEntity casesEntity, UUID id) {
        if (casesRepo.existsById(id)) {
            casesEntity.setId(id);
            casesRepo.save(casesEntity);
            return true;
        }

        return false;
    }

                // Удаляет дело по заданному ID
    @Override   // true - дело удалено, иначе false
    public boolean delete(UUID id) {
        if (casesRepo.existsById(id)) {
            casesRepo.deleteById(id);
            return true;
        }
        return false;
    }

    @Override   // Возвращает дела конкретного списка по имени или его части с сортировкой и пагинацией
    public Page<CasesEntity> readByListAndName(ListsEntity listsEntity, Pageable pageable, String name) {
        if(listsEntity != null)
            return casesRepo.findByListAndNameContaining(listsEntity, pageable, name);
        return null;
    }

    @Override
    public Page<CasesEntity> readByList(ListsEntity listsEntity, Pageable pageable) {
        if(listsEntity != null)
            return casesRepo.findByList(listsEntity, pageable);
        return null;
    }
}
