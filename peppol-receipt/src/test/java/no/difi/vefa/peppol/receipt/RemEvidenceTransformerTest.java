package no.difi.vefa.peppol.receipt;

import org.etsi.uri._02640.v2_.REMEvidenceType;
import org.testng.annotations.Test;

import javax.xml.bind.JAXBElement;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static org.testng.Assert.assertNotNull;

/**
 * Created by steinar on 08.11.2015.
 */
public class RemEvidenceTransformerTest {

    public void setUp() {

    }

    @Test
    public void buildStreamAndParseRemEvidence() throws Exception {

        // Obtains instance of the service which is the entry point to the Rem package
        RemEvidenceService remEvidenceService = TestResources.getRemEvidenceService();

        // Creates the sample REMEvidenceType
        JAXBElement<REMEvidenceType> remEvidenceInstance = TestResources.createSampleRemEvidence();

        // Creates the transformer
        RemEvidenceTransformer remEvidenceTransformer = remEvidenceService.createRemEvidenceTransformer();

        // where to place the transformed output
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // performs the actual transformation into XML representation
        remEvidenceTransformer.transformToXml(remEvidenceInstance, baos);

        System.out.println(baos.toString());

        // Attempts to parse the XML transformed REMEvidence
        JAXBElement<REMEvidenceType> remEvidenceTypeJAXBElement = remEvidenceTransformer.parse(new ByteArrayInputStream(baos.toByteArray()));

        assertNotNull(remEvidenceTypeJAXBElement);
    }

}