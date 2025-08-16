import greenfoot.*;

public class bg extends SWorld {
    private int spawnRate = 100;
    private int spawnTimer = 0;

    private GreenfootSound backgroundMusic = new GreenfootSound("bgMusic.mp3");
    private int numberOfLives = 5; // Number of lives
    private Heart[] hearts; // Array to hold heart objects
    private Player player; // Reference to the player

    public bg() {
        super(1000, 600, 1, 30000, 600);
        backgroundMusic.playLoop();

        // Set background image
        GreenfootImage bg = new GreenfootImage("map.png");
        setScrollingBackground(bg);
        bg.scale(8192, 323); // Match background dimensions
        setBackground(bg);

        // Create and add the invisible platform (full width of the background)
        InvisiblePlatform platform = new InvisiblePlatform(30000, 20);  // Full width of the world
        addObject(platform, 15000, getHeight() - 10);  // Place at the bottom of the world

        // Set the main actor (player) above the platform at the leftmost side
        player = new Player();
        addObject(player, 50, getHeight() - 60); // Ensuring the player starts above the platform

        // Initialize and add hearts
        hearts = new Heart[numberOfLives];
        for (int i = 0; i < numberOfLives; i++) {
            hearts[i] = new Heart();
            addObject(hearts[i], player.getX() - 40 + i * 20, player.getY() - player.getImage().getHeight() / 2 - 20); // Position hearts above the player
        }
        
        prepare();
    }

    public void act() {
        super.act();
        spawnEnemies();
        updateHeartPositions(); // Update heart positions to follow the player
    }

    public void started() {
        backgroundMusic.playLoop();
    }

    public void stopped() {
        backgroundMusic.stop();
    }

    private void spawnEnemies() {
        spawnTimer++;
        if (spawnTimer >= spawnRate) {
            int spawnX = getWidth();
            int spawnY = Greenfoot.getRandomNumber(getHeight() - 100);
            Enemy enemy = new Enemy();
            addObject(enemy, spawnX, spawnY);
            spawnTimer = 0;
        }
    }

    private void prepare() {
        // Add any extra platform setup logic here
    }

    public void loseLife() {
        if (numberOfLives > 0) {
            removeObject(hearts[numberOfLives - 1]);
            numberOfLives--;
        }
        if (numberOfLives <= 0) {
            Greenfoot.stop();
        }
    }

    private void updateHeartPositions() {
        for (int i = 0; i < numberOfLives; i++) {
            hearts[i].setLocation(player.getX() - 40 + i * 20, player.getY() - player.getImage().getHeight() / 2 - 20); // Adjust positions above the player
        }
    }
}