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

import org.testng.Assert;
import org.testng.annotations.Test;

public class TransportProfileTest {

    @Test
    public void simple() {
        Assert.assertTrue(TransportProfile.AS2_1_0.toString().contains("as2"));
        Assert.assertTrue(TransportProfile.AS2_1_0.toString().contains(TransportProfile.AS2_1_0.getValue()));

        Assert.assertTrue(TransportProfile.AS2_1_0.equals(TransportProfile.AS2_1_0));
        Assert.assertFalse(TransportProfile.AS2_1_0.equals(TransportProfile.AS4));
        Assert.assertFalse(TransportProfile.AS2_1_0.equals(TransportProtocol.AS2));
        Assert.assertFalse(TransportProfile.AS2_1_0.equals(null));

        Assert.assertNotNull(TransportProfile.AS2_1_0.hashCode());
    }
}
