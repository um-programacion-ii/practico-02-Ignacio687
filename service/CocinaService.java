package service;

import entity.Cocinable;
import entity.Despensa;
import entity.Reutilizable;
import entity.customExceptions.InvalidNameException;
import entity.customExceptions.StockInsuficienteException;
import entity.customExceptions.VidaUtilInsuficienteException;
import entity.recetas.Receta;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


public class CocinaService implements KitchenService{
    private Map<String, Receta> recetas;
    private DespensaService despensaService;

    public CocinaService() {
        this.recetas = new HashMap<>();
        this.despensaService = new DespensaService();
    }

    public CocinaService(Map<String, Receta> recetas, DespensaService despensaService) {
        this.recetas = recetas;
        this.despensaService = despensaService;
    }

    @Override
    public Map<String, Receta> getRecetas() {
        return recetas;
    }

    @Override
    public void setRecetas(Map<String, Receta> recetas) {
        this.recetas = recetas;
    }

    @Override
    public DespensaService getDespensaService() {
        return despensaService;
    }

    @Override
    public void setDespensaService(DespensaService despensaService) {
        this.despensaService = despensaService;
    }

    @Override
    public String toString() {
        return "CocinaService, " + KitchenService.showRecetas(this.recetas) + this.despensaService;
    }

    @Override
    public Receta getReceta(String name) throws InvalidNameException {
        Receta receta = this.recetas.get(name.trim().toLowerCase());
        if (receta == null) {
            throw new InvalidNameException("The recipe "+name+" doesn't exist.");
        } else {
            return receta;
        }
    }

    @Override
    public void prepareKitchen() {

    }

    @Override
    public void restockKitchen() {

    }

    @Override
    public String makeReceta(String name) throws InvalidNameException {
        Receta receta = this.getReceta(name);

        return this.getReceta(name).getPreparacion();
    }

}
