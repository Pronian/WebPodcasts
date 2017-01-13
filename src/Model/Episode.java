package Model;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Stores podcast episode data
 */
public class Episode
{
    private int id;
    private String name;
    private String description;
    private Date postedOn;

    public Episode(int id, String name, String description, Date postedOn)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.postedOn = postedOn;
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {

        return name;
    }

    public String getDescription()
    {
        return description;
    }

    public Date getPostedOn()
    {
        return postedOn;
    }

    public String getFormatedPostedOn()
    {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm zzz dd.MM.yyyy");
        return df.format(postedOn);
    }

    public String getImageLinkString()
    {
        StringBuilder sbImageLink = new StringBuilder();
        sbImageLink.append("\"background: url(podfiles/podcast-");
        sbImageLink.append(getId());
        sbImageLink.append(".jpg) no-repeat center center;\"");

        return sbImageLink.toString();
    }

    public String getMP3Link()
    {
        StringBuilder sbMP3link = new StringBuilder();
        sbMP3link.append("podfiles/podcast-");
        sbMP3link.append(getId());
        sbMP3link.append(".mp3");

        return sbMP3link.toString();
    }
}
