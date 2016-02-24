package util;

import java.util.Arrays;

/**
 * Created by Olav on 15.02.2016.
 */
public class MusicalKey {

    private static final int[] majorMode = new int[]{0,2,4,5,7,9,11};
    private static final int[] dorianMode = new int[]{0,2,3,5,7,9,10};
    private static final int[] phrygianMode = new int[]{0,1,3,5,6,8,10};
    private static final int[] lydianMode = new int[]{0,2,4,6,7,9,11};
    private static final int[] mixolydianMode = new int[]{0,2,4,5,7,9,10};
    private static final int[] minorMode = new int[]{0,2,3,5,7,8,10};
    private static final int[] locrianMode = new int[]{0,1,3,5,6,8,10};

    public final int rootPitch;
    public final Mode mode;
    public int[] scale;

    public MusicalKey(int rootPitch, Mode mode) {
        this.rootPitch = rootPitch;
        this.mode = mode;
        this.scale = buildKey(rootPitch, mode);
    }

    public int[] buildKey(int rootPitch, Mode mode) {
        int[] baseMode = null;
        switch(mode) {
            case MAJOR:
                baseMode = majorMode;
                break;
            case DORIAN:
                baseMode = dorianMode;
                break;
            case PHRYGIAN:
                baseMode = phrygianMode;
                break;
            case LYDIAN:
                baseMode = lydianMode;
                break;
            case MIXOLYDIAN:
                baseMode = mixolydianMode;
                break;
            case MINOR:
                baseMode = minorMode;
                break;
            case LOCRIAN:
                baseMode = locrianMode;
                break;
        }

        int[] key = new int[7];
        for (int i = 0; i < baseMode.length; i++) {
            key[i] = (baseMode[i] + rootPitch) % 12;
        }
        return key;
    }

    /**
     * Returns the step of the pitch, if it is in the scale. Returns -1 if the pitch is not in the scale.
     * @param pitch
     * @return Step number of the pitch if it is in the scale, -1 if the pitch is not in the scale.
     */
    public int pitchInKey(int pitch) {
        for (int i = 0; i < scale.length; i++) {
            if (scale[i] == pitch) {
                return i;
            }
        }
        return -1;
    }

    public enum Mode {
        MAJOR,
        DORIAN,
        PHRYGIAN,
        LYDIAN,
        MIXOLYDIAN,
        MINOR,
        LOCRIAN;
    }

    public static void main(String[] args) {

        MusicalKey key = new MusicalKey(0, Mode.MAJOR);
        System.out.println(Arrays.toString(key.scale));
        key = new MusicalKey(9, Mode.MINOR);
        System.out.println(Arrays.toString(key.scale));
    }

}
