package todo.API.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import todo.API.Entityes.ListsEntity;
import todo.API.repository.CasesRepo;
import todo.API.repository.ListsRepo;

import java.util.UUID;

@Service
public class ListsServiceImpl implements ListsService{
    @Autowired
    private ListsRepo listsRepo;

    @Autowired
    private CasesRepo casesRepo;

    @Override   // Создаёт список
    public void create(ListsEntity listsEntity) {
        listsRepo.save(listsEntity);
    }

    @Override   // Возвращает все списки
    public Page<ListsEntity> readAll(Pageable pageable) {
        return listsRepo.findAll(pageable);
    }

    @Override   // Возвращает списки по заголовку или его части
    public Page<ListsEntity> readByTitle(Pageable pageable, String title) {
        return listsRepo.findByTitleContaining(pageable, title);
    }

    @Override   // Возвращает один список по его ID
    public ListsEntity read(UUID id) {
        return listsRepo.getOne(id);
    }

    @Override   // Обновляет список
    public boolean update(ListsEntity listsEntity, UUID id) {
        if (listsRepo.existsById(id)) {
            listsEntity.setId(id);
            listsRepo.save(listsEntity);
            return true;
        }
        return false;
    }

    @Override   // Удаляет список
    public boolean delete(UUID id) {
        if (listsRepo.existsById(id)) {
            casesRepo.deleteAllByList_Id(id);
            return true;
        }
        return false;
    }
}
