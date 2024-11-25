package org.example.Views;

/**
 * Interfaz que define las operaciones básicas para una vista en la aplicación.
 * Las clases que implementen esta interfaz serán responsables de gestionar el cambio
 * de vistas y de mostrar los elementos visuales correspondientes en la interfaz gráfica.
 */
public interface View {

    /**
     * Cambia la vista actual y la actualiza en el contenedor de la interfaz gráfica.
     * Este método es invocado cuando se necesita cambiar entre diferentes vistas,
     * actualizando el contenido visual en el panel principal.
     */
    void changeView();

    /**
     * Muestra los elementos visuales de la vista actual, como botones, etiquetas o tablas.
     * Este método es invocado para construir o actualizar la interfaz visual con los componentes
     * correspondientes a la vista que se está mostrando.
     */
    void showView();
}
