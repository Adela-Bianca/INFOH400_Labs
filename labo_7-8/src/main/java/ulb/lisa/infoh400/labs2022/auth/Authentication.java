/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ulb.lisa.infoh400.labs2022.auth;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import ulb.lisa.infoh400.labs2022.controller.UserJpaController;
import ulb.lisa.infoh400.labs2022.model.User;
/**
 *
 * @author Adela
 */
public class Authentication {
     private static final EntityManagerFactory emfac = Persistence.createEntityManagerFactory("infoh400_PU");
    private static final UserJpaController userCtrl = new UserJpaController(emfac);
    private static User loggedInUser = null;
    
    public static boolean hasUser(){
        List<User> users = userCtrl.findUserEntities();
        return users.size() > 0;
    }
    
    public static void createUser(String username, char[] password){
        try {
            String saltedHash = PasswordFactory.getSaltedHash(password);
            
            User user = new User();
            user.setUsername(username);
            user.setPassword(saltedHash);
            userCtrl.create(user);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(Authentication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static boolean login(String username, char[] password){
        try {
            User user = userCtrl.findUserByUsername(username);
            if( user == null ) return false;
            
            if( PasswordFactory.check(password, user.getPassword()) ){
                loggedInUser = user;
                user.setLastLogin(new Date());
                userCtrl.edit(user);
                
                return true;
            }
        } catch (InvalidKeySpecException | NoSuchAlgorithmException ex) {
            Logger.getLogger(Authentication.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Authentication.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    public static User getUser(){
        return loggedInUser;
    }    
}
