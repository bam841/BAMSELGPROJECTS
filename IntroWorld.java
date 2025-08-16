import greenfoot.*;

public class IntroWorld extends World {
    public IntroWorld() {    
        super(1200, 600, 1); // Set world size
        setBackground(new GreenfootImage("INTRO.png")); // Set intro background
    }

    public void act() {
        if (Greenfoot.isKeyDown("enter")) { // If Enter is pressed
            Greenfoot.setWorld(new bg()); // Switch to the game world
        }
    }
}
