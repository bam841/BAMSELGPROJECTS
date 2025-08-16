import greenfoot.*;

public class Player extends Actor {
    private int speed = 5;
    private int jumpStrength = 15;
    private int ySpeed = 0;
    private boolean onGround = false;
    private int fireRateCooldown = 20;
    private int fireRateTimer = 0;
    private boolean facingRight = true;
    
    private int health = 100; // Player's initial health
    private int invincibilityTimer = 30; // Prevent instant multiple hits

    private GreenfootSound gunshotSound = new GreenfootSound("gunshot.mp3");

    private GreenfootImage[] idleFrames = { new GreenfootImage("idle01.png"), new GreenfootImage("idle02.png") };
    private GreenfootImage[] runFrames = { new GreenfootImage("run01.png"), new GreenfootImage("run02.png") };

    private GreenfootImage[] idleFramesFlipped = new GreenfootImage[2];
    private GreenfootImage[] runFramesFlipped = new GreenfootImage[2];

    private int animationTimer = 0;
    private int currentFrame = 0;

    public Player() {
        for (int i = 0; i < idleFrames.length; i++) {
            idleFrames[i].scale(idleFrames[i].getWidth() / 3, idleFrames[i].getHeight() / 3);
            idleFramesFlipped[i] = new GreenfootImage(idleFrames[i]);
            idleFramesFlipped[i].mirrorHorizontally();
        }
        for (int i = 0; i < runFrames.length; i++) {
            runFrames[i].scale(runFrames[i].getWidth() / 3, runFrames[i].getHeight() / 3);
            runFramesFlipped[i] = new GreenfootImage(runFrames[i]);
            runFramesFlipped[i].mirrorHorizontally();
        }
        setImage(idleFrames[0]);
    }

    public void addedToWorld(World world) {
    }

    public void act() {
        handleMovement();
        handleJump();
        handleShooting();
        applyGravity();
        checkGround();
        updateAnimation();
        checkEnemyCollision();

        if (invincibilityTimer > 0) {
            invincibilityTimer--; // Reduce invincibility time
        }
    }

    private void handleMovement() {
        boolean moving = false;
        if (Greenfoot.isKeyDown("a")) {
            setLocation(getX() - speed, getY());
            facingRight = false;
            moving = true;
        }
        if (Greenfoot.isKeyDown("d")) {
            setLocation(getX() + speed, getY());
            facingRight = true;
            moving = true;
        }

        if (!moving && onGround) {
            setImage(facingRight ? idleFrames[currentFrame % idleFrames.length] : 
                                   idleFramesFlipped[currentFrame % idleFramesFlipped.length]);
        }
    }

    private void handleJump() {
        if (Greenfoot.isKeyDown("w") && onGround) {
            ySpeed = -jumpStrength;
            onGround = false;
        }
    }

    private void handleShooting() {
        if (Greenfoot.isKeyDown("space") && fireRateTimer <= 0) {
            Bullet bullet = new Bullet();
            int bulletOffsetX = facingRight ? 10 : -10;
            getWorld().addObject(bullet, getX() + bulletOffsetX, getY());
            bullet.setDirection(facingRight ? 0 : 180);
            gunshotSound.stop();
            gunshotSound.play();
            fireRateTimer = fireRateCooldown;
        }
        if (fireRateTimer > 0) {
            fireRateTimer--;
        }
    }

    private void applyGravity() {
        ySpeed++;
        setLocation(getX(), getY() + ySpeed);

        Actor platform = getOneObjectAtOffset(0, getImage().getHeight() / 2, InvisiblePlatform.class);
        if (platform != null) {
            setLocation(getX(), platform.getY() - platform.getImage().getHeight() / 2 - getImage().getHeight() / 2);
            ySpeed = 0;
            onGround = true;
        } else {
            onGround = false;
        }
    }

    private void checkGround() {
        onGround = getOneObjectAtOffset(0, getImage().getHeight() / 2, InvisiblePlatform.class) != null;
    }

    private void updateAnimation() {
        animationTimer++;
        if (animationTimer >= 10) {
            animationTimer = 0;
            currentFrame++;

            if (Greenfoot.isKeyDown("a") || Greenfoot.isKeyDown("d")) {
                setImage(facingRight ? runFrames[currentFrame % runFrames.length] : 
                                       runFramesFlipped[currentFrame % runFramesFlipped.length]);
            } else {
                setImage(facingRight ? idleFrames[currentFrame % idleFrames.length] : 
                                       idleFramesFlipped[currentFrame % idleFramesFlipped.length]);
            }
        }
    }

    private void checkEnemyCollision() {
        Actor enemy = getOneIntersectingObject(Enemy.class);
        if (enemy != null && invincibilityTimer <= 0) {
            health -= 10; // Reduce health by 10 when touching an enemy

            bg world = (bg) getWorld();
            world.loseLife();
            
            invincibilityTimer = 30; // Prevent instant multiple hits

            if (health <= 0) {
                getWorld().removeObject(this);
                Greenfoot.stop();
            }
        }
    }
}