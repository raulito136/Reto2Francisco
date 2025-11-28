package org.example.retofrancisco2.services;

import org.example.retofrancisco2.copias.Copia;

/**

 * Servicio singleton para manejar la copia actualmente seleccionada.
 */
public class CopiaService {

    private static CopiaService instance;
    private Copia copiaSeleccionada;

    /**

     * Constructor privado para implementar el patrón singleton.
     */
    private CopiaService() {
    }

    /**

     * Devuelve la instancia única del servicio.
     *
     * @return la instancia de CopiaService.
     */
    public static CopiaService getInstance() {
        if (instance == null) {
            instance = new CopiaService();
        }
        return instance;
    }

    /**

     * Establece la copia actualmente seleccionada.
     *
     * @param copia la copia a seleccionar.
     */
    public void setCopiaSeleccionada(Copia copia) {
        this.copiaSeleccionada = copia;
    }

    /**

     * Obtiene la copia actualmente seleccionada.
     *
     * @return la copia seleccionada.
     */
    public Copia getCopiaSeleccionada() {
        return copiaSeleccionada;
    }
}
