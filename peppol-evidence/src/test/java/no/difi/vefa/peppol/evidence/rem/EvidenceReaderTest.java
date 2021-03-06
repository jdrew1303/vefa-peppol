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

package no.difi.vefa.peppol.evidence.rem;

import no.difi.vefa.peppol.evidence.lang.RemEvidenceException;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.InputStream;

public class EvidenceReaderTest {

    @Test
    public void simpleConstructor() {
        new EvidenceReader();
    }

    @Test(expectedExceptions = RemEvidenceException.class)
    public void exceptionOnInputStreamError() throws Exception {
        EvidenceReader.read(Mockito.mock(InputStream.class));
    }

    @Test(expectedExceptions = RemEvidenceException.class, expectedExceptionsMessageRegExp = "Version .*")
    public void exceptionOnInvalidVersion() throws Exception {
        EvidenceReader.read(getClass().getResourceAsStream("/test-version-1.xml"));
    }

    @Test
    public void readSimpleFile() throws Exception {
        Evidence evidence = EvidenceReader.read(getClass().getResourceAsStream("/sample-signed-formatted-rem.xml"));
        Assert.assertFalse(evidence.hasPeppolExtensionValues());
    }
}
