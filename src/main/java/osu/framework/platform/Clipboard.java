package osu.framework.platform;

import java.awt.image.BufferedImage;

/**
 * Allows placing and retrieving data from the clipboard.
 */
public abstract class Clipboard {
    /**
     * Retrieve text from the clipboard
     */
    public abstract String getText();

    /**
     * Copy text to the clipboard
     */
    public abstract void setText(String text);

    /**
     * Retrieve image from the clipboard
     */
    public abstract BufferedImage getImage();

    /**
     * Copy an image to the clipboard
     * @return whether the image was successfully copied
     */
    public abstract boolean setImage(BufferedImage image);
}
