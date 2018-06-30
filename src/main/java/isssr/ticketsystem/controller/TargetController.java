package isssr.ticketsystem.controller;

import isssr.ticketsystem.dao.TargetDao;
import isssr.ticketsystem.entity.Target;
import isssr.ticketsystem.enumeration.State;
import isssr.ticketsystem.enumeration.SystemRole;
import isssr.ticketsystem.enumeration.TargetState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Service

public class TargetController {

    private final TargetDao targetDao;

    private final StateMachineController stateMachineController;

    @Autowired
    public TargetController(StateMachineController stateMachineController, TargetDao targetDao) {
        this.stateMachineController = stateMachineController;
        this.targetDao = targetDao;
    }

    @Transactional
    public @NotNull Target insertTarget(@NotNull Target target) {
        Target createdTarget = targetDao.save(target);
        return createdTarget;
    }

    @Transactional
    public @NotNull Target updateTarget(@NotNull Long id, @NotNull Target updatedData) {

        Target toBeUpdatedTarget = targetDao.getOne(id);
        toBeUpdatedTarget.updateTarget(updatedData);
        Target updatedTarget = targetDao.save(toBeUpdatedTarget);
        return updatedTarget;
    }

    @Transactional
    public List<Target> getTarget() {

        return targetDao.findAll();
    }
    @Transactional
    public Target changeStateTarget(@NotNull Long id, TargetState targetState) {

        Target toBeUpdatedTarget = targetDao.getOne(id);
        toBeUpdatedTarget.setTargetState(targetState);
        Target updatedTarget = targetDao.save(toBeUpdatedTarget);
        return updatedTarget;
    }

    @Transactional
    public List<Target> getActiveTarget() {

        return targetDao.getActiveTarget(TargetState.ACTIVE);
    }

    public List<String> getActualStates(Long targetID, String role) {
        Target target = getTargetById(targetID);
        String stateMachineName = target.getStateMachineName();
        return stateMachineController.getActualStates(stateMachineName,role);

    }

    private Target getTargetById(Long targetID) {
        return targetDao.getOne(targetID);

    }

    public ArrayList<ArrayList<String>> getNextStates(Long targetID, String currentState) {
        Target target = getTargetById(targetID);
        String stateMachineName = target.getStateMachineName();
        return stateMachineController.getNextStates(stateMachineName,currentState);

    }
}
