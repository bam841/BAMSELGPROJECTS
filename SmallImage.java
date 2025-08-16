import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class tuutorials here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import greenfoot.*;

public class SmallImage extends Actor {
    public SmallImage(int width, int height) {  
        GreenfootImage image = new GreenfootImage("tutorials.jpg"); // Correct class name
        image.scale(width, height); // Resize image
        setImage(image); // Set resized image to actor
    }
}
