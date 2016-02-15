package util;

import genetics.HarmonyGenotype;
import org.jfugue.player.Player;

/**
 * Created by Olav on 15.02.2016.
 */
public class ChordBuilder {

    public static byte[] getChord(int rootStep, int pitches, MusicalKey key) {
        byte[] chord = new byte[pitches];
        for (int i  = 0; i < pitches; i++) {
            chord[i] =(byte) key.key[(rootStep + i*2) % 7];
        }
        return chord;
    }

    public static byte[] getChord(int rootStep, int pitches, int padding, MusicalKey key) {
        byte[] chord = new byte[pitches + padding];
        for (int i  = 0; i < pitches; i++) {
            chord[i] = (byte) key.key[(rootStep + i*2) % key.key.length];
        }
        for (int i  = 0; i < padding; i++) {
            chord[pitches + i] = -1;
        }
        return chord;
    }

    public static void main(String[] args) {
        MusicalKey key = new MusicalKey(0, MusicalKey.Mode.MAJOR);

        HarmonyGenotype hg = new HarmonyGenotype(4);
        hg.chords[0] = ChordBuilder.getChord(0, 3, 1, key);
        hg.chords[1] = ChordBuilder.getChord(5, 3, 1, key);
        hg.chords[2] = ChordBuilder.getChord(3, 3, 1, key);
        hg.chords[3] = ChordBuilder.getChord(4, 4, key);

        MusicParser parser = new MusicParser();
        Player player = new Player();
        String chords = "Rw | " + parser.parseChords(hg);
        System.out.println(chords);
        player.play(chords);

        key = new MusicalKey(0, MusicalKey.Mode.MINOR);

        hg = new HarmonyGenotype(4);
        hg.chords[0] = ChordBuilder.getChord(0, 3, 1, key);
        hg.chords[1] = ChordBuilder.getChord(5, 3, 1, key);
        hg.chords[2] = ChordBuilder.getChord(3, 3, 1, key);
        hg.chords[3] = ChordBuilder.getChord(4, 4, key);

        parser = new MusicParser();
        player = new Player();
        chords = "Rw | " + parser.parseChords(hg);
        System.out.println(chords);
        player.play(chords);
    }
}
