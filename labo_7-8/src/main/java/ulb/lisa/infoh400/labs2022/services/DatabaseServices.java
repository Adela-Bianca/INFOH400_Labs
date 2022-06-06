/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ulb.lisa.infoh400.labs2022.services;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Adela
 */
public class DatabaseServices {
    private final EntityManagerFactory emfac = Persistence.createEntityManagerFactory("infoh400_PU");
    
    public DatabaseServices() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatabaseServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public EntityManagerFactory getEntityManagerFactory(){
        return emfac;
    }
}
