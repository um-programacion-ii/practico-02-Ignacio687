package service;

import entity.customExceptions.InvalidNameException;
import entity.customExceptions.StockInsuficienteException;
import entity.customExceptions.VidaUtilInsuficienteException;
import entity.recetas.Receta;

import java.util.Map;
import java.util.stream.Collectors;

public interface KitchenService {

    Map<String, Receta> getRecetas();

    void setRecetas(Map<String, Receta> recetas);

    DespensaService getDespensaService();

    void setDespensaService(DespensaService despensaService);

    static String showRecetas(Map<String, Receta> recetas) {
        return "recetas: " + recetas.values().stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n\n- ", "\n\n- ", ""));
    }

    Receta getReceta(String name) throws InvalidNameException;

    void restockKitchen();

    void prepareKitchen();

    String makeReceta(String name) throws InvalidNameException;
}
