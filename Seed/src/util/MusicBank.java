package util;

import genetics.MelodyContainer;

/**
 * Created by Olav on 19.05.2016.
 */
public class MusicBank {

    /**
     * Puts "Lisa gikk til skolen" into the melody container in C major.
     * @param mc - MelodyContainer with at least 8 measures.
     */
    public static void putLisaMelody(MelodyContainer mc) {
        mc.melody[0] = 60 + 12;
        mc.melody[4] = 62 + 12;
        mc.melody[8] = 64 + 12;
        mc.melody[12] = 65 + 12;
        mc.melody[16] = 67 + 12;
        mc.melody[24] = 67 + 12;
        mc.melody[32] = 69 + 12;
        mc.melody[36] = 69 + 12;
        mc.melody[40] = 69 + 12;
        mc.melody[44] = 69 + 12;
        mc.melody[48] = 67 + 12;
        mc.melody[64] = 65 + 12;
        mc.melody[68] = 65 + 12;
        mc.melody[72] = 65 + 12;
        mc.melody[76] = 65 + 12;
        mc.melody[80] = 64 + 12;
        mc.melody[88] = 64 + 12;
        mc.melody[96] = 62 + 12;
        mc.melody[100] = 62 + 12;
        mc.melody[104] = 62 + 12;
        mc.melody[108] = 62 + 12;
        mc.melody[112] = 60 + 12;
    }

    /**
     * Puts "Hit me baby one more time" into the melody container, in C minor.
     * @param mc - A MelodyContainer with at least 12 bars.
     */
    public static void putHitMe(MelodyContainer mc) {
        mc.melody[2] = 60 + 12;
        mc.melody[4] = 60 + 12;
        mc.melody[6] = 60 + 12;
        mc.melody[8] = 60 + 12;
        mc.melody[10] = 60 + 12;
        mc.melody[14] = 60 + 12;

        mc.melody[16] = 62 + 12;
        mc.melody[18] = 59 + 12;
        mc.melody[20] = 55 + 12;
        mc.melody[22] = 55 + 12;
        mc.melody[28] = 53 + 12;
        mc.melody[30] = 55 + 12;

        mc.melody[46] = 55 + 12;

        mc.melody[48] = 60 + 12;
        mc.melody[50] = 60 + 12;
        mc.melody[52] = 62 + 12;
        mc.melody[54] = 63 + 12;
        mc.melody[56] = 62 + 12;
        mc.melody[60] = 60 + 12;

        mc.melody[66] = 60 + 12;
        mc.melody[68] = 60 + 12;
        mc.melody[70] = 60 + 12;
        mc.melody[72] = 60 + 12;
        mc.melody[74] = 60 + 12;
        mc.melody[78] = 60 + 12;

        mc.melody[16 + 64] = 62 + 12;
        mc.melody[18 + 64] = 59 + 12;
        mc.melody[20 + 64] = 55 + 12;
        mc.melody[22 + 64] = 55 + 12;
        mc.melody[28 + 64] = 53 + 12;
        mc.melody[30 + 64] = 55 + 12;

        mc.melody[46 + 64] = 55 + 12;

        mc.melody[48  + 64] = 60 + 12;
        mc.melody[50  + 64] = 60 + 12;
        mc.melody[52  + 64] = 62 + 12;
        mc.melody[54  + 64] = 63 + 12;
        mc.melody[56 + 64] = 62 + 12;
        mc.melody[60 + 64] = 60 + 12;

        //SHOW ME
        mc.melody[128] = 63 + 12;
        mc.melody[132] = 63 + 12;
        mc.melody[136] = 63 + 12;
        mc.melody[138] = 63 + 12;
        mc.melody[140] = 65 + 12;
        mc.melody[142] = 63 + 12;

        mc.melody[144] = 62 + 12;
        mc.melody[148] = 62 + 12;

        mc.melody[156] = 60 + 12;
        mc.melody[158] = 62 + 12;

        mc.melody[160] = 63 + 12;
        mc.melody[164] = 63 + 12;
        mc.melody[168] = 63 + 12;
        mc.melody[170] = 63 + 12;
        mc.melody[172] = 65 + 12;
        mc.melody[174] = 63 + 12;
        mc.melody[176] = 67 + 12;
        mc.melody[180] = 67 + 12;
        mc.melody[184] = 72 + 12;
        mc.melody[186] = 67 + 12;
        mc.melody[188] = 65 + 12;
        mc.melody[190] = 63 + 12;

    }
}
