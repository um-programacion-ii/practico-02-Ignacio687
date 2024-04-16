package service;

import entity.*;
import entity.customExceptions.InvalidNameException;
import entity.customExceptions.ObjectAlreadyExistsException;
import entity.customExceptions.StockInsuficienteException;
import entity.customExceptions.VidaUtilInsuficienteException;
import entity.recetas.*;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main (String[] args) {
        // ################ TP 2 PUNTO 2 ################
        System.out.print("\u001B[31m");
        System.out.println("\nTP2 Punto2:\n");
        System.out.print("\u001B[0m");
        Chef chef = new Chef("Fernando", 2);
        Despensa despensa = new Despensa();
        DespensaService despensaService = new DespensaService(despensa);
        Map<String, Receta> recetas = new HashMap<>() {{
            put("ensalada", new Ensalada());
            put("fideos", new Fideos());
            put("huevo duro", new HuevoDuro());
            put("pizza", new Pizza());
        }};
        CocinaService cocinaService = new CocinaService(recetas, despensaService);
        for (Receta receta: cocinaService.getRecetas().values()) {
            for (Cocinable cocinable: receta.getIngredientes().values()) {
                despensa.addIngrediente(new Ingrediente(cocinable.getNombre(), cocinable.getCantidad()));
            }
            for (Reutilizable reutilizable: receta.getUtensilios().values()) {
                try {
                    despensa.addUtensilio(new Utensilio(reutilizable.getNombre(), reutilizable.getVidaUtil()));
                } catch (ObjectAlreadyExistsException e) {
                    try {
                        Reutilizable utensilio = despensa.inspectUtensilio(reutilizable.getNombre());
                        utensilio.setVidaUtil(utensilio.getVidaUtil()+reutilizable.getVidaUtil());
                    } catch (InvalidNameException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }
        System.out.println("El chef "+ chef + " va a preparar las siguientes recetas:");
        System.out.println(CocinaService.showRecetas(cocinaService.getRecetas()));
        try {
            String[] nombresRecetas = {"Ensalada", "Fideos", "Huevo Duro", "Pizza"};
            System.out.println("Estado de despensa: \n"+despensa);
            for (String receta: nombresRecetas) {
                System.out.println("\nPreparación receta "+receta+":  \n");
                System.out.println(cocinaService.makeReceta(receta));
                System.out.println(despensaService.getDespensa());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("\n\nUn cliente quiere otra porción de fideos: ");
        try {
            cocinaService.makeReceta(" Fideos");
        } catch (StockInsuficienteException | VidaUtilInsuficienteException | InvalidNameException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("\nAgregando Ingredientes a la despensa: ");
        try {
            for (Cocinable cocinable: cocinaService.getReceta("Fideos").getIngredientes().values()) {
                despensa.addIngrediente(new Ingrediente(cocinable.getNombre(), cocinable.getCantidad()));
            }
            System.out.println(Despensa.showItems(Despensa.getMapOf(Cocinable.class, despensa.getDespensables())));
        } catch (InvalidNameException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("\nPreparando nuevamente: ");
        try {
            System.out.println(cocinaService.makeReceta("Fideos"));
        } catch (StockInsuficienteException | VidaUtilInsuficienteException | InvalidNameException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("\nRenovando utensilios");
        cocinaService.getDespensaService().renovarUtensilios();
        System.out.println("\nPreparando nuevamente: ");
        try {
            System.out.println(cocinaService.makeReceta("Fideos"));
        } catch (StockInsuficienteException | VidaUtilInsuficienteException | InvalidNameException e) {
            System.out.println(e.getMessage());
        }
    }
}