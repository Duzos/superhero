package mc.duzo.timeless.client.sounds.manager;

import mc.duzo.timeless.client.sounds.thruster.ThrusterSoundHandler;

public class SoundManager {
    private static ThrusterSoundHandler thrusters;

    public static ThrusterSoundHandler thrusters() {
        if (thrusters == null) {
            thrusters = new ThrusterSoundHandler();
        }

        return thrusters;
    }
}
