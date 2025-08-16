import greenfoot.*;

public class Heart extends Actor {
    public Heart() {
        GreenfootImage heartImage = new GreenfootImage("heart.png");
        heartImage.scale(15, 15); // Adjust the size of the heart image to make it smaller
        setImage(heartImage);
    }
}