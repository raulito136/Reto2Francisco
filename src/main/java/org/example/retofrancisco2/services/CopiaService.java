package org.example.retofrancisco2.services;

import org.example.retofrancisco2.copias.Copia;

public class CopiaService {

    private static CopiaService instance;
    private Copia copiaSeleccionada;

    private CopiaService() {
    }

    public static CopiaService getInstance() {
        if (instance == null) {
            instance = new CopiaService();
        }
        return instance;
    }

    public void setCopiaSeleccionada(Copia copia) {
        this.copiaSeleccionada = copia;
    }

    public Copia getCopiaSeleccionada() {
        return copiaSeleccionada;
    }
}

