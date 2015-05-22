/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatabuilder;

import interfaces.IState;
import interfaces.ITransition;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Mikael
 */
public enum DeadState implements IState {
    INSTANCE;
    
    public static final String NAME = "DeadState";

    @Override
    public IState transition(Symbol a) {
        return this;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public List<ITransition> getTransitions(){
        return new LinkedList<>();
    }

    @Override
    public boolean isFinal() {
        return false;
    }
}
