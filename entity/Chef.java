package entity;

public class Chef {
    private String nombre;
    private Integer estrellasMichelin;

    public Chef(String nombre, Integer estrellasMichelin) {
        this.nombre = nombre;
        this.estrellasMichelin = estrellasMichelin;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getEstrellasMichelin() {
        return estrellasMichelin;
    }

    public void setEstrellasMichelin(Integer estrellasMichelin) {
        this.estrellasMichelin = estrellasMichelin;
    }

    @Override
    public String toString() {
        return "Chef " + nombre +
                ", Estrellas Michelin: " + estrellasMichelin;
    }
}
