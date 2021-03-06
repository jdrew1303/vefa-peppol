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

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import no.difi.vefa.peppol.common.code.Service;
import no.difi.vefa.peppol.common.lang.PeppolLoadingException;
import no.difi.vefa.peppol.mode.Mode;
import no.difi.vefa.peppol.security.api.CertificateValidator;
import no.difi.vefa.peppol.security.lang.PeppolSecurityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.cert.X509Certificate;

public class ModeDetector {

    private static Logger logger = LoggerFactory.getLogger(ModeDetector.class);

    public static Mode detect(X509Certificate certificate) throws PeppolLoadingException {
        Config config = ConfigFactory.load();

        for (String token : config.getObject("mode").keySet()) {
            if (!token.equals("default")) {
                try {
                    Mode mode = Mode.of(config, token);
                    mode.initiate("security.validator.class", CertificateValidator.class)
                            .validate(Service.ALL, certificate);
                    return mode;
                } catch (PeppolSecurityException e) {
                    logger.info("Detection error ({}): {}", token, e.getMessage());
                }
            }
        }

        throw new PeppolLoadingException(
                String.format("Unable to detect mode for certificate '%s'.", certificate.getSubjectDN().toString()));
    }
}
