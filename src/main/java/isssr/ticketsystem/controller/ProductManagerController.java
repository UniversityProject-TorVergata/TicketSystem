package isssr.ticketsystem.controller;

import isssr.ticketsystem.dao.ProductManagerDao;
import isssr.ticketsystem.entity.TeamCoordinator;
import isssr.ticketsystem.exception.NotFoundEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class ProductManagerController {

    @Autowired
    private ProductManagerDao productManagerDao;

    @Transactional
    public @NotNull TeamCoordinator insertProductManager(@NotNull TeamCoordinator teamCoordinator) {
        TeamCoordinator createdCompany = productManagerDao.save(teamCoordinator);
        return createdCompany;
    }

    @Transactional
    public @NotNull TeamCoordinator updateProductManager(@NotNull Long id, @NotNull TeamCoordinator updatedData) throws NotFoundEntityException {

        TeamCoordinator toBeUpdatedTeamCoordinator = productManagerDao.getOne(id);

        if (toBeUpdatedTeamCoordinator == null)
            throw new NotFoundEntityException();

        toBeUpdatedTeamCoordinator.update(updatedData);
        TeamCoordinator updatedTeamCoordinator = productManagerDao.save(toBeUpdatedTeamCoordinator);

        return updatedTeamCoordinator;
    }

    public TeamCoordinator findProductManagerById(@NotNull Long id) {
        TeamCoordinator foundTeamCoordinator = productManagerDao.getOne(id);
        return foundTeamCoordinator;
    }

    public boolean deleteProductManager(@NotNull Long id) {
        if (!productManagerDao.existsById(id)) {
            return false;
        }
        productManagerDao.deleteById(id);
        return true;
    }

    public List<TeamCoordinator> getProductManager() {

        return productManagerDao.findAll();
    }
}
