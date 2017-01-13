package Logic;

import Model.Episode;
import Model.Feed;

import java.io.FileOutputStream;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class PodcastRSSFeedWriter
{
    private String outputFile;
    private Feed rssfeed;

    public PodcastRSSFeedWriter(Feed rssfeed, String outputFile)
    {
        this.rssfeed = rssfeed;
        this.outputFile = outputFile;
    }

    public void write() throws Exception
    {
        // create a XMLOutputFactory
        XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();

        // create XMLEventWriter
        XMLEventWriter eventWriter = outputFactory.createXMLEventWriter(new FileOutputStream(outputFile));

        // create a EventFactory
        XMLEventFactory eventFactory = XMLEventFactory.newInstance();
        XMLEvent end = eventFactory.createDTD("\n");

        Characters chars = eventFactory.createCharacters("\n");
        // create and write Start Tag
        StartDocument startDocument = eventFactory.createStartDocument();

        eventWriter.add(startDocument);

        // create open tag
        eventWriter.add(end);

        StartElement rssStart = eventFactory.createStartElement("", "", "rss");
        eventWriter.add(rssStart);
        eventWriter.add(eventFactory.createAttribute("version", "2.0"));
        eventWriter.add(chars);

        eventWriter.add(eventFactory.createStartElement("", "", "channel"));
        eventWriter.add(chars);

        // Write the different nodes
        createNode(eventWriter, "title", rssfeed.getTitle());

        createNode(eventWriter, "link", rssfeed.getLink());

        createNode(eventWriter, "description", rssfeed.getDescription());

        createNode(eventWriter, "language", rssfeed.getLanguage());

        createNode(eventWriter, "copyright", rssfeed.getCopyright());

        createNode(eventWriter, "pubdate", rssfeed.getPubDate());

        for (Episode entry : rssfeed.getEpisodes())
        {
            eventWriter.add(eventFactory.createStartElement("", "", "item"));
            eventWriter.add(chars);
            createNode(eventWriter, "title", entry.getName());
            createNode(eventWriter, "pubDate", Utilities.DateToPubDate(entry.getPostedOn()));
            createNode(eventWriter, "description", entry.getDescription());
            createNode(eventWriter, "link", rssfeed.getLinkPrefix() + entry.getId());
            createNode(eventWriter, "author", rssfeed.getAuthor() );
            createNode(eventWriter, "guid", rssfeed.getLinkPrefix() + entry.getId() );
            eventWriter.add(eventFactory.createStartElement("","","enclosure"));
            eventWriter.add(eventFactory.createAttribute("url",rssfeed.getFileLinkPrefix() + entry.getMP3Link()));
            eventWriter.add(eventFactory.createAttribute("type","audio/mpeg"));
            eventWriter.add(eventFactory.createEndElement("", "", "enclosure"));
            eventWriter.add(chars);
            eventWriter.add(eventFactory.createEndElement("", "", "item"));
            eventWriter.add(chars);
        }

        eventWriter.add(chars);
        eventWriter.add(eventFactory.createEndElement("", "", "channel"));
        eventWriter.add(chars);
        eventWriter.add(eventFactory.createEndElement("", "", "rss"));

        eventWriter.add(chars);

        eventWriter.add(eventFactory.createEndDocument());

        eventWriter.close();
    }

    private void createNode(XMLEventWriter eventWriter, String name, String value) throws XMLStreamException
    {
        XMLEventFactory eventFactory = XMLEventFactory.newInstance();
        //XMLEvent end = eventFactory.createDTD("\n");
        //XMLEvent tab = eventFactory.createDTD("\t");
        // create Start node
        StartElement sElement = eventFactory.createStartElement("", "", name);
        eventWriter.add(eventFactory.createCharacters("\t"));
        eventWriter.add(sElement);
        // create Content
        Characters characters = eventFactory.createCharacters(value);
        eventWriter.add(characters);
        // create End node
        EndElement eElement = eventFactory.createEndElement("", "", name);
        eventWriter.add(eElement);
        eventWriter.add(eventFactory.createCharacters("\n"));
    }
}