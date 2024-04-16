package service;

import entity.Cocinable;
import entity.Despensa;
import entity.Reutilizable;
import entity.customExceptions.InvalidNameException;
import entity.customExceptions.StockInsuficienteException;
import entity.customExceptions.VidaUtilInsuficienteException;

import java.util.List;
import java.util.Set;

public class DespensaService {
    private Despensa despensa;

    public DespensaService() {
        this.despensa = new Despensa();
    }

    public DespensaService(Despensa despensa) {
        this.despensa = despensa;
    }

    public Despensa getDespensa() {
        return despensa;
    }

    public void setDespensa(Despensa despensa) {
        this.despensa = despensa;
    }

    @Override
    public String toString() {
        return "DespensaService: \n" + despensa;
    }

    public List<Cocinable> verifyStock(Set<Cocinable> ingredienteSet) {
        Despensa ingredientesFaltantes = new Despensa();
        for (Cocinable ingrediente: ingredienteSet) {
            try {
                Cocinable ingredienteDisp = this.despensa.inspectIngrediente(ingrediente.getNombre());
                if (ingredienteDisp.getCantidad() < ingrediente.getCantidad()) {
                    ingrediente.setCantidad(ingrediente.getCantidad()-ingredienteDisp.getCantidad());
                    ingredientesFaltantes.addIngrediente(ingrediente);
                }
            } catch (InvalidNameException e) {
                ingredientesFaltantes.addIngrediente(ingrediente);
            }
        }
         return ingredientesFaltantes.getDespensables().values().stream()
                 .map(Cocinable.class::cast)
                 .toList();
    }

    public List<Reutilizable> verifyVidaUtil(Set<Reutilizable> utensilioSet) {
        Despensa utensiliosFaltantes = new Despensa();
        for (Reutilizable utensilio: utensilioSet) {
            try {
                Reutilizable utensilioDisp = this.despensa.inspectUtensilio(utensilio.getNombre());
                if (utensilioDisp.getVidaUtil() < utensilio.getVidaUtil()) {
                    utensilio.setVidaUtil(utensilio.getVidaUtil()-utensilioDisp.getVidaUtil());
                    utensiliosFaltantes.getDespensables().put(utensilio.getNombre(), utensilio);
                }
            } catch (InvalidNameException e) {
                utensiliosFaltantes.getDespensables().put(utensilio.getNombre(), utensilio);
            }
        }
        return utensiliosFaltantes.getDespensables().values().stream()
                .map(Reutilizable.class::cast)
                .toList();
    }

    public void renovarUtensilios() {
       Despensa.getMapOf(Reutilizable.class, this.despensa.getDespensables()).values().stream()
               .filter(obj -> obj.getVidaUtil() == 0)
               .forEach(Reutilizable::renew);
    }
}
