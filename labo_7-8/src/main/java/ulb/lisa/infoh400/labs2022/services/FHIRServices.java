/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ulb.lisa.infoh400.labs2022.services;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import java.util.ArrayList;
import java.util.List;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Bundle.BundleEntryComponent;
import org.hl7.fhir.r4.model.ContactPoint;
import org.hl7.fhir.r4.model.IdType;
import ulb.lisa.infoh400.labs2022.model.Patient;
import ulb.lisa.infoh400.labs2022.model.Person;

/**
 *
 * @author Adela
 */
public class FHIRServices {
    
    
    public static Patient getPatient(org.hl7.fhir.r4.model.Patient patientResource){
        Patient patient = new Patient();
        Person person = new Person();
        person.setFirstName(patientResource.getNameFirstRep().getGivenAsSingleString());
        person.setFamilyName(patientResource.getNameFirstRep().getFamily());
        person.setDateOfBirth(patientResource.getBirthDate());
        patient.setIdperson(person);
        for( ContactPoint contact: patientResource.getTelecom() ){
            if( contact.getSystem() == ContactPoint.ContactPointSystem.PHONE ){
                patient.setPhonenumber(contact.getValue());
            }
        }
        
        return patient;
    }
    
    public static org.hl7.fhir.r4.model.Patient getPatient(Patient patientInTable){
        org.hl7.fhir.r4.model.Patient p = new org.hl7.fhir.r4.model.Patient();
        p.addName().setFamily(patientInTable.getIdperson().getFamilyname());
        p.getNameFirstRep().addGiven(patientInTable.getIdperson().getFirstName());
        p.setBirthDate(patientInTable.getIdperson().getDateOfBirth());
        p.addTelecom().setValue(patientInTable.getPhonenumber());
        p.setId(new IdType("Patient", String.valueOf(patientInTable.getIdpatient())));
        
        return p;
    }
    
    public static ArrayList<org.hl7.fhir.r4.model.Patient> getPatients(List<Patient> patientsInTable){
        ArrayList<org.hl7.fhir.r4.model.Patient> patients = new ArrayList();
        for( Patient p: patientsInTable ){
            patients.add(getPatient(p));
        }
        
        return patients;
    }
    
    public Patient castFromFHIRIntoPatient(org.hl7.fhir.r4.model.Patient fhirPatient){
        Patient patient = new Patient();
        Person person = new Person();
        person.setDateOfBirth(fhirPatient.getBirthDate());
        person.setFamilyName(fhirPatient.getNameFirstRep().getFamily());
        person.setFirstName(fhirPatient.getNameFirstRep().getGivenAsSingleString());
        patient.setIdperson(person);
        patient.setStatus("active");
        return patient;
    }
    
    public List<Patient> searchPatients(String host, String familyName){
        ArrayList<Patient> patients = new ArrayList();
        
        FhirContext context = FhirContext.forR4();
        IGenericClient client = context.newRestfulGenericClient(host);
        
        Bundle resultBundle = client
                .search()
                .forResource(org.hl7.fhir.r4.model.Patient.class)
                .where(org.hl7.fhir.r4.model.Patient.FAMILY.matches().value(familyName))
                .returnBundle(Bundle.class)
                .execute();
        
        for (BundleEntryComponent  entry : resultBundle.getEntry()){
            org.hl7.fhir.r4.model.Patient patient = (org.hl7.fhir.r4.model.Patient) entry.getResource();
            patients.add(castFromFHIRIntoPatient(patient));
            
        
        }
        //System.out.println("Found " + resultBundle.getEntry().size() + " patients named " + familyName);
        
        return patients;
        
    }
}
