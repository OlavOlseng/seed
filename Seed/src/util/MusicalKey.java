package util;

import java.util.Arrays;

/**
 * Created by Olav on 15.02.2016.
 */
public class MusicalKey {

    private static final int[] minorMode = new int[]{0,2,3,5,7,8,10};
    private static final int[] majorMode = new int[]{0,2,4,5,7,9,11};

    public final int rootPitch;
    public final Mode mode;
    public int[] key;

    public MusicalKey(int rootPitch, Mode mode) {
        this.rootPitch = rootPitch;
        this.mode = mode;
        this.key = buildKey(rootPitch, mode);
    }

    public int[] buildKey(int rootPitch, Mode mode) {
        int[] baseMode = null;
        switch(mode) {
            case MAJOR:
                baseMode = majorMode;
                break;
            case MINOR:
                baseMode = minorMode;
                break;
            }

        int[] key = new int[7];
        for (int i = 0; i < baseMode.length; i++) {
            key[i] = (baseMode[i] + rootPitch) % 12;
        }
        return key;
    }

    /**
     * Returns the step of the pitch, if it is in the key. Returns -1 if the pitch is not in the key.
     * @param pitch
     * @return Step number of the pitch if it is in the key, -1 if the pitch is not in the key.
     */
    public int pitchInKey(int pitch) {
        for (int i = 0; i < key.length; i++) {
            if (key[i] == pitch) {
                return i;
            }
        }
        return -1;
    }

    public enum Mode {
        MAJOR,
        MINOR;
    }

    public static void main(String[] args) {

        MusicalKey key = new MusicalKey(0, Mode.MAJOR);
        System.out.println(Arrays.toString(key.key));
        key = new MusicalKey(9, Mode.MINOR);
        System.out.println(Arrays.toString(key.key));
    }

}
