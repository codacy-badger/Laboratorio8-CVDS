
package edu.eci.cvds.sampleprj.dao;
/**
 * @author (Nicolas Aguilera y Carlos Ramirez)
 * @version (12 of March of 2020)
 */
public class PersistenceException extends Exception{
    public PersistenceException(String mensaje,Exception e){
        super(mensaje,e);
    }
    public PersistenceException(String mensaje){
        super(mensaje);
    }
}