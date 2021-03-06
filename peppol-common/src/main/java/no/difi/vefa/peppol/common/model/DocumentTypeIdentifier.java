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

package no.difi.vefa.peppol.common.model;

import no.difi.vefa.peppol.common.lang.PeppolParsingException;

import java.io.Serializable;

/**
 * DocumentTypeIdentifier is a combination of XML type and customizationId.
 */
public class DocumentTypeIdentifier extends AbstractQualifiedIdentifier implements Serializable {

    private static final long serialVersionUID = -3748163459655880167L;

    public static final Scheme DEFAULT_SCHEME = Scheme.of("busdox-docid-qns");

    public static DocumentTypeIdentifier of(String identifier) {
        return new DocumentTypeIdentifier(identifier, DEFAULT_SCHEME);
    }

    public static DocumentTypeIdentifier of(String identifier, Scheme scheme) {
        return new DocumentTypeIdentifier(identifier, scheme);
    }

    public static DocumentTypeIdentifier parse(String str) throws PeppolParsingException {
        String[] parts = str.split("::", 2);

        if (parts.length != 2)
            throw new PeppolParsingException(String.format("Unable to parse document type identifier '%s'.", str));

        return of(parts[1], Scheme.of(parts[0]));
    }

    protected DocumentTypeIdentifier(String value, Scheme scheme) {
        super(value, scheme);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DocumentTypeIdentifier that = (DocumentTypeIdentifier) o;

        return toString().equals(that.toString());
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public String toString() {
        return String.format("%s::%s", scheme, identifier);
    }
}
