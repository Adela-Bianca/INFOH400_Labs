/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ulb.lisa.fhirserver2022;

import ca.uhn.fhir.rest.annotation.IdParam;
import ca.uhn.fhir.rest.annotation.Read;
import ca.uhn.fhir.rest.annotation.RequiredParam;
import ca.uhn.fhir.rest.annotation.Search;
import ca.uhn.fhir.rest.param.StringParam;
import ca.uhn.fhir.rest.server.IResourceProvider;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import org.hl7.fhir.instance.model.api.IIdType;
import org.hl7.fhir.r4.model.Patient;
import ulb.lisa.infoh400.labs2022.controller.PatientJpaController;
import ulb.lisa.infoh400.labs2022.services.DatabaseServices;
import ulb.lisa.infoh400.labs2022.services.FHIRServices;

/**
 *
 * @author Adrien Foucart
 */
public class PatientResourceProvider implements IResourceProvider {
    
    private final DatabaseServices db = new DatabaseServices();
    private final EntityManagerFactory emfac = db.getEntityManagerFactory();
    private final PatientJpaController patientCtrl = new PatientJpaController(emfac);
    
    @Override
    public Class<Patient> getResourceType() {
        return Patient.class;
    }
    
    // Read method will be accessed by:
    // /FHIRServer/Patient/{id}    
    @Read()
    public Patient getResourceById(@IdParam IIdType pid){
        return FHIRServices.getPatient(patientCtrl.findPatient(Integer.valueOf(pid.getIdPart())));
    }
    
    // Search method will be accessed by:
    // /FHIRServer/Patient?family={name}
    @Search()
    public List<Patient> getPatient(@RequiredParam(name = Patient.SP_FAMILY) StringParam name) { 
        return FHIRServices.getPatients(patientCtrl.findByFamilyName(name.getValue()));
    }
}
