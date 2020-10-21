package todo.API.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import todo.API.Entityes.ListsEntity;
import todo.API.repository.ListsRepo;

@Service
public class ListsServiceImpl implements ListsService{
    @Autowired
    private ListsRepo listsRepo;

    @Override
    public void create(ListsEntity listsEntity) {
        listsRepo.save(listsEntity);
    }

    @Override
    public Page<ListsEntity> readAll(Pageable pageable) {
        return listsRepo.findAll(pageable);
    }

    @Override
    public ListsEntity read(long id) {
        return listsRepo.getOne(id);
    }

    @Override
    public boolean update(ListsEntity listsEntity, long id) {
        if (listsRepo.existsById(id)) {
            listsEntity.setId(id);
            listsRepo.save(listsEntity);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(long id) {
        if (listsRepo.existsById(id)) {
            listsRepo.deleteById(id);
            return true;
        }
        return false;
    }
}
