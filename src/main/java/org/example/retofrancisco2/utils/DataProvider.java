package org.example.retofrancisco2.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**

 * Proveedor de acceso a la base de datos mediante Hibernate.
 * Implementa el patrón singleton para mantener una única SessionFactory.
 */
public class DataProvider {

    private static SessionFactory sessionFactory = null;

    /**

     * Constructor privado para evitar la instanciación de la clase.
     */
    private DataProvider() {}

    /**

     * Devuelve la Se única para interactuar con la base de datos.
     * Si no existe, la crea y la configura usando las variables de entorno:
     * DB_USER y DB_PASSWORD.
     *
     * @return la SessionFactory configurada.
     */
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            var configuration = new Configuration().configure();
            configuration.setProperty("hibernate.connection.username", System.getenv("DB_USER"));
            configuration.setProperty("hibernate.connection.password", System.getenv("DB_PASSWORD"));
            sessionFactory = configuration.buildSessionFactory();
        }
        return sessionFactory;
    }
}
