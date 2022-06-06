/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ulb.lisa.infoh400.labs2022.services;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.protocol.ReceivingApplication;
import ca.uhn.hl7v2.protocol.ReceivingApplicationException;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Adela
 */
public class ADTReceiverApplication implements ReceivingApplication{
 
    @Override
    public Message processMessage(Message msg, Map<String, Object> map) throws ReceivingApplicationException, HL7Exception {
        try {
            String encodedMessage = new DefaultHapiContext().getPipeParser().encode(msg);
            System.out.println("Received message:\n" + encodedMessage + "\n\n");
            
            // Now generate a simple acknowledgment message and return it
            return msg.generateACK();
        } catch (IOException ex) {
            Logger.getLogger(ADTReceiverApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean canProcess(Message msg) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
