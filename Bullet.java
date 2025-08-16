import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Bullet extends Actor {
    private int speed = 10;

    public Bullet() {
        resizeBullet();
        setRotation(0); // Default direction (to the right)
    }

    // Resizes the bullet
    private void resizeBullet() {
        GreenfootImage image = getImage();
        image.scale(image.getWidth() / 50, image.getHeight() / 50); // Adjust bullet size (1/50 scale)
        setImage(image);
    }

    public void act() {
        move(speed); // Move the bullet in the current direction

        checkCollision();
        checkOutOfBounds();
    }

    private void checkCollision() {
        // Check for collision with Enemy
        Enemy enemy = (Enemy) getOneIntersectingObject(Enemy.class);
        if (enemy != null) {
            // Remove both bullet and enemy when collision occurs
            World world = getWorld();
            if (world != null) {
                world.removeObject(enemy);
               // world.removeObject(this);
            }
        }
    }

    private void checkOutOfBounds() {
        // Remove bullet when it goes off-screen
        if (isAtEdge()) {
            getWorld().removeObject(this);
        }
    }

    // Set the direction of the bullet based on the player's facing direction
    public void setDirection(int rotation) {
        setRotation(rotation); // Set the bullet's rotation to match the given direction
    }
}
