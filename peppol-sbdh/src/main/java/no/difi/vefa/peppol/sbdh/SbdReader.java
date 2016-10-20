package no.difi.vefa.peppol.sbdh;

import no.difi.vefa.peppol.common.model.Header;
import no.difi.vefa.peppol.sbdh.lang.SbdhException;
import no.difi.vefa.peppol.sbdh.util.XMLBinaryInputStream;
import no.difi.vefa.peppol.sbdh.util.XMLStreamPartialReaderWrapper;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

public class SbdReader implements Closeable {

    private XMLStreamReader reader;

    private Header header;

    public static SbdReader newInstance(InputStream inputStream) throws SbdhException {
        try {
            return newInstance(SbdhHelper.XML_INPUT_FACTORY.createXMLStreamReader(inputStream));
        } catch (XMLStreamException e) {
            throw new SbdhException(e.getMessage(), e);
        }
    }

    public static SbdReader newInstance(XMLStreamReader xmlStreamReader) throws SbdhException {
        return new SbdReader(xmlStreamReader);
    }

    private SbdReader(XMLStreamReader reader) throws SbdhException {
        this.reader = reader;

        try {
            // First element, SBD expected.
            if (reader.getEventType() != XMLStreamConstants.START_ELEMENT)
                reader.nextTag();

            if (!reader.getName().equals(SbdhHelper.QNAME_SBD))
                throw new SbdhException("Element 'StandardBusinessDocument' not found as first element.");

            // Read header
            reader.nextTag();
            if (!reader.getName().equals(SbdhHelper.QNAME_SBDH))
                throw new SbdhException("Element 'StandardBusinessDocumentHeader' not found as first element in 'StandardBusinessDocument'.");

            this.header = SbdhReader.read(reader);

            // Go to payload
            if (reader.getEventType() != XMLStreamConstants.START_ELEMENT)
                reader.nextTag();
            if (reader.getEventType() != XMLStreamConstants.START_ELEMENT)
                throw new SbdhException("Payload not found.");
        } catch (XMLStreamException e) {
            throw new SbdhException(e.getMessage(), e);
        }
    }

    public Header getHeader() {
        return header;
    }

    public Type getType() {
        return reader.getName().equals(SbdhHelper.QNAME_BINARY_CONTENT) ? Type.BINARY : Type.XML;
    }

    public XMLStreamReader xmlReader() {
        return new XMLStreamPartialReaderWrapper(reader);
    }

    public InputStream binaryReader() throws XMLStreamException {
        return new XMLBinaryInputStream(xmlReader());
    }

    @Override
    public void close() throws IOException {
        try {
            reader.close();
        } catch (XMLStreamException e) {
            throw new IOException(e.getMessage(), e);
        }
    }

    public enum Type {
        BINARY, XML
    }
}