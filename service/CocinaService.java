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


public class CocinaService {
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

    public Map<String, Receta> getRecetas() {
        return recetas;
    }

    public void setRecetas(Map<String, Receta> recetas) {
        this.recetas = recetas;
    }

    public DespensaService getDespensaService() {
        return despensaService;
    }

    public void setDespensaService(DespensaService despensaService) {
        this.despensaService = despensaService;
    }

    @Override
    public String toString() {
        return "CocinaService, " + showRecetas(this.recetas) + this.despensaService;
    }

    public static String showRecetas(Map<String, Receta> recetas) {
        return "recetas: " + recetas.values().stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n\n- ", "\n\n- ", ""));
    }

    public Receta getReceta(String name) throws InvalidNameException {
        Receta receta = this.recetas.get(name.trim().toLowerCase());
        if (receta == null) {
            throw new InvalidNameException("The recipe "+name+" doesn't exist.");
        } else {
            return receta;
        }
    }

    public String makeReceta(String name) throws StockInsuficienteException, VidaUtilInsuficienteException,
            InvalidNameException {
        List<Cocinable> ingredientesFaltantes = this.despensaService.verifyStock(new HashSet<>(this.getReceta(name).getIngredientes().values()));
        if (!ingredientesFaltantes.isEmpty()) {
            throw new StockInsuficienteException("Faltan los siguientes ingredientes:  "
                    + Despensa.showItems(ingredientesFaltantes.stream()
                    .collect(Collectors.toMap(Cocinable::getNombre, Function.identity()))));
        }
        List<Reutilizable> utensiliosFaltantes = this.despensaService.verifyVidaUtil(new HashSet<>(this.getReceta(name).getUtensilios().values()));
        if (!utensiliosFaltantes.isEmpty()) {
            throw new VidaUtilInsuficienteException("Tiempo faltante en los siguientes utensilios:  "
                    + Despensa.showItems(utensiliosFaltantes.stream()
                    .collect(Collectors.toMap(Reutilizable::getNombre, Function.identity()))));
        }
        for (Cocinable ingrediente: this.getReceta(name).getIngredientes().values()) {
            this.getDespensaService().getDespensa().getIngrediente(ingrediente.getNombre(), ingrediente.getCantidad());
        }
        for (Reutilizable reutilizable: this.getReceta(name).getUtensilios().values()) {
            this.getDespensaService().getDespensa().useUtensilio(reutilizable.getNombre(), reutilizable.getVidaUtil());
        }
        return this.getReceta(name).getPreparacion();
    }

}
