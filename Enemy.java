import greenfoot.*;

public class Enemy extends Actor {
    private GreenfootImage[] animationFrames;
    private int currentFrame = 0;
    private int animationSpeed = 5;
    private int animationTimer = 0;
    private int speed = 5;

    public Enemy() {
        animationFrames = new GreenfootImage[3];
        animationFrames[0] = new GreenfootImage("fly.png");
        animationFrames[1] = new GreenfootImage("fly1.png");
        animationFrames[2] = new GreenfootImage("fly2.png");

        for (GreenfootImage img : animationFrames) {
            img.scale(300, 150);
        }

        setImage(animationFrames[0]);
    }

    public void act() {
        if (getWorld() == null) return;

        moveTowardPlayer();
        animate();
    }

    private void animate() {
        animationTimer++;
        if (animationTimer >= animationSpeed) {
            currentFrame = (currentFrame + 1) % animationFrames.length;
            setImage(animationFrames[currentFrame]);
            animationTimer = 0;
        }
    }

    private void moveTowardPlayer() {
        if (getWorld().getObjects(Player.class).isEmpty()) return;
        Player player = (Player) getWorld().getObjects(Player.class).get(0);
        int dx = player.getX() - getX();
        int dy = player.getY() - getY();
        double angle = Math.atan2(dy, dx);
        int moveX = (int) (speed * Math.cos(angle));
        int moveY = (int) (speed * Math.sin(angle));
        setLocation(getX() + moveX, getY() + moveY);
    }
}
