package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores an RSS feed
 */
public class Feed
{

    final String title;
    final String link;
    final String description;
    final String language;
    final String copyright;
    final String pubDate;
    final String author;
    final String linkPrefix;
    final String fileLinkPrefix;

    private List<Episode> entries = new ArrayList<Episode>();

    public Feed(String title, String link, String description, String language, String copyright, String pubDate, String author, String linkPrefix, String fileLinkPrefix)
    {
        this.title = title;
        this.link = link;
        this.description = description;
        this.language = language;
        this.copyright = copyright;
        this.pubDate = pubDate;
        this.author = author;
        this.linkPrefix = linkPrefix;
        this.fileLinkPrefix = fileLinkPrefix;
    }

    public List<Episode> getEpisodes()
    {
        return entries;
    }

    public void setEntries(List<Episode> entries)
    {
        this.entries = entries;
    }

    public String getTitle()
    {
        return title;
    }

    public String getLink()
    {
        return link;
    }

    public String getDescription()
    {
        return description;
    }

    public String getLanguage()
    {
        return language;
    }

    public String getCopyright()
    {
        return copyright;
    }

    public String getPubDate()
    {
        return pubDate;
    }

    public String getAuthor()
    {
        return author;
    }

    public String getLinkPrefix()
    {
        return linkPrefix;
    }

    public String getFileLinkPrefix()
    {
        return fileLinkPrefix;
    }

    @Override
    public String toString()
    {
        return "Feed [copyright=" + copyright + ", description=" + description
                + ", language=" + language + ", link=" + link + ", pubDate="
                + pubDate + ", title=" + title + "]";
    }

}