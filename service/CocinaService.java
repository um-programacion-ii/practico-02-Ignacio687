package service;

import entity.*;
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
    public void restockKitchen() {
        this.despensaService.restockIngredientes();
        this.despensaService.renovarUtensilios();
    }

    @Override
    public void prepareKitchen() {
        for (Receta receta: this.recetas.values()) {
            Set<Cocinable> ingredientes = receta.getIngredientes().values().stream()
                    .map(obj -> new Ingrediente(obj.getNombre(), 0))
                    .map(Cocinable.class::cast)
                    .collect(Collectors.toSet());
            this.despensaService.addIngredientes(ingredientes);
            Set<Reutilizable> utensilios = receta.getUtensilios().values().stream()
                    .map(obj -> new Utensilio(obj.getNombre(), (obj.getVidaUtil()*20)))
                    .map(Reutilizable.class::cast)
                    .collect(Collectors.toSet());
            this.despensaService.addUtensilios(utensilios);
        }
        this.restockKitchen();
    }

    @Override
    public String makeReceta(String name) throws InvalidNameException {
        Receta receta = this.getReceta(name);
        try {
            this.despensaService.useIngredientes(new HashSet<>(receta.getIngredientes().values()));
        } catch (StockInsuficienteException e) {

        }

        return this.getReceta(name).getPreparacion();
    }

}
