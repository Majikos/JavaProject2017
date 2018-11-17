package game;


public class Wave {
    public static double difficulty = 1;
    public static int waveNum = 0;
    private static int eCount = 0;

    public static void spawnWave() {
        int zone, tempx, tempy;
        difficulty += 0.02;
        eCount = waveNum/2 + 6;
        for (int i = 0; i < eCount; i++) {
            zone = calculateZone();
            tempx = calculateX(zone);
            tempy = calculateY(zone);
            GameWindow.enemies.add(new Enemy(tempx, tempy,
                    calculateVel(), calculateVel()));
        }
        for (int i = 0; i < (eCount / 6); i++) {
            zone = calculateZone();
            tempx = calculateX(zone);
            tempy = calculateY(zone);
            GameWindow.enemies.add(new SpecialEnemy(tempx, tempy));
        }
        for(int i=0;i<(eCount /4 );i++){
            zone = calculateZone();
            tempx = calculateX(zone);
            tempy = calculateY(zone);
            GameWindow.enemies.add(new TrackingEnemy(tempx, tempy));
        }

        if(waveNum % 3 == 0)
            GameWindow.powerups.add(new Powerups(randomNum(-2,4),randomNum(20,730),randomNum(20,530)));
    }

    private static int calculateZone(){
        int zone = (int)(Math.random() * 4) + 1;
        return zone;
    }
    private static int calculateX(int zone){
        if(zone == 1 || zone == 3)
            return randomNum(10,740);
        else if(zone == 2)
            return randomNum(650,740);
        else
            return randomNum(10,120);
    }
    private static int calculateY(int zone){
        if(zone == 1)
            return randomNum(10,120);
        else if(zone == 2 || zone == 4)
            return randomNum(10,540);
        else
            return randomNum(450,540);
    }

    private static double calculateVel(){
        double vel = randomNum(-2,2) * difficulty;
        if(vel > -0.5 && vel < 0)
            vel -= 1;
        else if(vel < 0.5 && vel >= 0)
            vel += 1;
        return vel;
    }

    private static int randomNum(int min, int max) {
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }
}
