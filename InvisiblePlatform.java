import greenfoot.*;  

public class InvisiblePlatform extends Actor {
    public InvisiblePlatform(int width, int height) {
        GreenfootImage image = new GreenfootImage(width, height);
        image.setColor(Color.RED); // Make it clearly visible
        image.fill();  // Fill the platform
        image.setTransparency(255); // Fully visible for debugging
        setImage(image);
    }
}