package Model;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;

public class FileWriter
{
    public static Boolean WriteFileFromStream(InputStream stream, String filePath, String fileExtension)
    {
        try
        {
            File imgTarget = new File(filePath + fileExtension);
            Files.copy(stream,imgTarget.toPath());
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
