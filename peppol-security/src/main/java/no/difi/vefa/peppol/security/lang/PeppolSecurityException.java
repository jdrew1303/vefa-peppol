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

package no.difi.vefa.peppol.security.lang;

import no.difi.vefa.peppol.common.lang.PeppolException;

public class PeppolSecurityException extends PeppolException {

    private static final long serialVersionUID = 6928682319726226728L;

    public PeppolSecurityException(String message) {
        super(message);
    }

    public PeppolSecurityException(String message, Throwable cause) {
        super(message, cause);
    }
}
