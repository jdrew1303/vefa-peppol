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

package no.difi.vefa.peppol.security;

import no.difi.certvalidator.Validator;
import no.difi.vefa.peppol.common.lang.PeppolLoadingException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.security.cert.X509Certificate;

public class ModeDetectorTest {

    @Test
    public void simpleConstructor() {
        new ModeDetector();
    }

    @Test(expectedExceptions = PeppolLoadingException.class)
    public void simpleDetectError() throws Exception {
        X509Certificate certificate = Validator.getCertificate(getClass().getResourceAsStream("/web-difi.cer"));
        ModeDetector.detect(certificate).getString("security.pki");
    }

    @Test
    public void simpleDetectProduction() throws Exception {
        X509Certificate certificate = Validator.getCertificate(getClass().getResourceAsStream("/ap-difi-prod.cer"));
        Assert.assertEquals(ModeDetector.detect(certificate).getString("security.pki"), "/pki/peppol-production.xml");
    }

    @Test
    public void simpleDetectTest() throws Exception {
        X509Certificate certificate = Validator.getCertificate(getClass().getResourceAsStream("/ap-difi-test.cer"));
        Assert.assertEquals(ModeDetector.detect(certificate).getString("security.pki"), "/pki/peppol-test.xml");
    }
}
