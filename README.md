# prog2.2024.practico02

Consigna: tomando como contexto la consigna del TP 1 realizar lo siguiente:

1. Luego de clonar el repo crear una nueva Branch protection rule con las siguientes especificaciones
   
    a. Branch name pattern -> main
   
    b. Tildar SOLAMENTE "Require a pull request before merging" y "Do not allow bypassing the above settings"

2. Reutilizando las clases del TP 1 se harán las siguientes modificaciones:
   
    a. Agregar la interface Cocinable, sólo se puede cocinar con los objetos que implementen esta interface
   
    b. Agregar la interface entity.Despensable, esto permitirá generar una clase nueva Utensilio para agregar a las recetas los utensilios a usarse. Ahora en la despensa no habrá sólo ingredientes, sino todos aquellos objetos que sean entity.Despensable's
   
    c. Convertir los arreglos usados en el TP anterior en colecciones. Sugerencia, para acceder a los Ingrediente's que están en la Despensa más rápidamente cambiar el arreglo de Ingrediente's de la despensa por un Map de entity.Despensable's donde la clave sea un string con el nombre del objeto.
   
    d. Agregar la interface Reutilizable para los Utensilio's que tenga el atributo vidaUtil que se irá consumiendo a medida que sea utilizado para cocinar
   
    e. Cambiar las recetas para agregar los Utensilio's usados, cada receta además de disminuir el stock de la Despensa, debería verificar si en la Despensa están los utensilios necesarios para la cocina y el servicio CocinaService debe disminuir el tiempo de vidaUtil del utensilio usado
   
    f. Agregar una excepción propia VidaUtilInsuficiente para el caso que el tiempo de vida restante no alcance para cocinar
   
    g. Agregar una excepción propia StockInsuficiente para el caso que la Despensa no tenga el Ingrediente suficiente para la receta
   
    h. Agregar un servicio DespensaService que se encargue de las verificaciones de stock y tiempo de vida útil antes de cocinar, también agregar un método renovarUtensilios que permita "resetear" el tiempo de vita útil de los Utensilio's inutilizables
   
    i. Tratar de usar stream's donde sea posible
