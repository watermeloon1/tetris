
import main.Settings;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.image.BufferedImage;

public class SettingsTest {

    String path;
    BufferedImage picture;

    @Before
    public void setUp(){
        path = "/background.png";
    }

    @Test
    public void testImportImage(){
        picture = Settings.loadImage(path);
        Assert.assertNotNull(picture);
    }

}