/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ulb.lisa.infoh400.labs2022.services;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.app.Connection;
import ca.uhn.hl7v2.app.Initiator;
import ca.uhn.hl7v2.llp.LLPException;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.v23.message.ADT_A01;
import ca.uhn.hl7v2.model.v23.segment.MSH;
import ca.uhn.hl7v2.model.v23.segment.PID;
import ca.uhn.hl7v2.parser.Parser;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import ulb.lisa.infoh400.labs2022.model.Patient;

/**
 *
 * @author Adela
 */
public class HL7Services {
    public ADT_A01 create_ADT_A01(Patient patient){
        ADT_A01 adt = new ADT_A01();
        try {
            adt.initQuickstart("ADT", "A01", "p");
        
        MSH mshSegment = adt.getMSH();
        mshSegment.getSendingApplication().getNamespaceID().setValue("HIS");
        mshSegment.getSequenceNumber().setValue("123");
        
        PID pid = adt.getPID();
        pid.getPatientName(0).getFamilyName().setValue(patient.getIdperson().getFamilyName());
        pid.getPatientName(0).getGivenName().setValue(patient.getIdperson().getFirstName());
        pid.getPatientIDInternalID(0).getID().setValue(String.valueOf(patient.getIdpatient()));
        
        } catch (HL7Exception | IOException ex) {
            Logger.getLogger(HL7Services.class.getName()).log(Level.SEVERE, null, ex);
        }
        return adt;
    }
    
    public void send_ADT_A01(ADT_A01 adtMessage, String host, int port){
        try {
            HapiContext context = new DefaultHapiContext();
            
            // The false is to indicate that there is no secure connection
            Connection connection = context.newClient( host,port,false);
            
            Initiator initiator = connection.getInitiator();
            Message response = initiator.sendAndReceive(adtMessage);
            Parser p = context.getPipeParser();
            System.out.println(p.encode(response));
        } catch (HL7Exception | LLPException | IOException ex) {
            Logger.getLogger(HL7Services.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
            
            
}
