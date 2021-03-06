/*
 * Copyright 2016-2017 Direktoratet for forvaltning og IKT
 *
 * Licensed under the EUPL, Version 1.1 or – as soon they
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "Licence");
 *
 * You may not use this work except in compliance with the Licence.
 *
 * You may obtain a copy of the Licence at:
 *
 * https://joinup.ec.europa.eu/community/eupl/og_page/eupl
 *
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
 */

package no.difi.vefa.peppol.sbdh;

import no.difi.vefa.peppol.common.model.*;
import no.difi.vefa.peppol.sbdh.lang.SbdhException;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.xml.stream.XMLStreamReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Date;

public class SbdhReaderTest {

    @Test
    public void simple() throws Exception {
        Header header = Header.newInstance()
                .sender(ParticipantIdentifier.of("9908:987654325"))
                .receiver(ParticipantIdentifier.of("9908:123456785"))
                .process(ProcessIdentifier.of("urn:www.cenbii.eu:profile:bii04:ver1.0"))
                .documentType(DocumentTypeIdentifier.of("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice" +
                        "##urn:www.cenbii.eu:transaction:biicoretrdm010:ver1.0:#urn:www.peppol.eu:bis:peppol4a:ver1.0::2.0"))
                .instanceType(InstanceType.of("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice", "2.0"))
                .creationTimestamp(new Date())
                .identifier(InstanceIdentifier.generateUUID());

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        SbdhWriter.write(byteArrayOutputStream, header);

        Header parsedHeader = SbdhReader.read(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));

        Assert.assertEquals(parsedHeader, header);
    }

    @Test(expectedExceptions = SbdhException.class)
    public void triggerExceptionUsingInputStream() throws Exception {
        SbdhReader.read(Mockito.mock(InputStream.class));
    }

    @Test(expectedExceptions = SbdhException.class)
    public void triggerExceptionUsingXMLStreamReader() throws Exception {
        SbdhReader.read(Mockito.mock(XMLStreamReader.class));
    }

    @Test
    public void simpleConstructor() {
        new SbdhReader();
    }
}
