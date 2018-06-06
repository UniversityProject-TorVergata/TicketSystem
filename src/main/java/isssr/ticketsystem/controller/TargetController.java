package isssr.ticketsystem.controller;

import isssr.ticketsystem.dao.TargetDao;
import isssr.ticketsystem.entity.Target;
import isssr.ticketsystem.enumeration.TargetState;
import isssr.ticketsystem.exception.NotFoundEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class TargetController {

    @Autowired
    private TargetDao targetDao;

    @Transactional
    public @NotNull Target insertProduct(@NotNull Target target) {
        Target createdTarget = targetDao.save(target);
        return createdTarget;
    }

    @Transactional
    public @NotNull Target updateProduct(@NotNull Long id, @NotNull Target updatedData) throws NotFoundEntityException {

        Target toBeUpdatedTarget = targetDao.getOne(id);

        if (toBeUpdatedTarget == null)
            throw new NotFoundEntityException();

        toBeUpdatedTarget.updateTarget(updatedData);
        Target updatedTarget = targetDao.save(toBeUpdatedTarget);

        return updatedTarget;
    }

    public Target findProductById(@NotNull Long id) {
        Target foundTarget = targetDao.getOne(id);
        return foundTarget;
    }

    public boolean deleteTarget(@NotNull Long id) {
        if (!targetDao.existsById(id)) {
            return false;
        }
        targetDao.deleteById(id);
        return true;
    }

    public List<Target> getTarget() {

        return targetDao.findAll();
    }

    public Target changeStateProduct(@NotNull Long id, TargetState targetState) throws NotFoundEntityException {

        Target toBeUpdatedTarget = targetDao.getOne(id);

        if (toBeUpdatedTarget == null)
            throw new NotFoundEntityException();

        toBeUpdatedTarget.setTargetState(targetState);
        Target updatedTarget = targetDao.save(toBeUpdatedTarget);

        return updatedTarget;
    }
}
