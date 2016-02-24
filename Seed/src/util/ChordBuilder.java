package util;

import genetics.ChordContainer;
import org.jfugue.player.Player;

/**
 * Created by Olav on 15.02.2016.
 */
public class ChordBuilder {

    public static byte[] getChord(int rootStep, int pitches, MusicalKey key) {
        return getChord(rootStep, pitches, 0, key);
    }

    public static byte[] getChord(int rootStep, int pitches, int padding, MusicalKey key) {
        return getChord(rootStep, pitches, padding, key, false);
    }

    public static byte[] getChord(int rootStep, int pitches, int padding, MusicalKey key, boolean dominant) {
        byte[] chord = new byte[pitches + padding];
        for (int i  = 0; i < pitches; i++) {
            chord[i] = (byte) key.scale[(rootStep + i*2) % key.scale.length];
            if(i == 1 && dominant) {
                byte change = 1;
                chord[i] = (byte) ((chord[i] + change) % 12);
            }
        }
        for (int i  = 0; i < padding; i++) {
            chord[pitches + i] = ChordContainer.NO_PITCH;
        }
        return chord;
    }

    public static void main(String[] args) {
        MusicalKey key = new MusicalKey(0, MusicalKey.Mode.MAJOR);

        ChordContainer hg = new ChordContainer(4, key);
        hg.chords[0] = ChordBuilder.getChord(0, 3, 1, key);
        hg.chords[1] = ChordBuilder.getChord(5, 3, 1, key);
        hg.chords[2] = ChordBuilder.getChord(3, 3, 1, key);
        hg.chords[3] = ChordBuilder.getChord(4, 4, key);

        MusicParser parser = new MusicParser();
        Player player = new Player();
        String chords = "Rw | " + parser.parseChords(hg);
        System.out.println(chords);
        //player.play(chords);

        key = new MusicalKey(9, MusicalKey.Mode.MINOR);

        hg = new ChordContainer(4, key);
        hg.chords[0] = ChordBuilder.getChord(0, 4, 0, key);
        hg.chords[1] = ChordBuilder.getChord(5, 5, 0, key);
        hg.chords[2] = ChordBuilder.getChord(3, 4, 0, key);
        hg.chords[3] = ChordBuilder.getChord(4, 4, 0,  key, true);

        parser = new MusicParser();
        player = new Player();
        chords = "Rw | " + parser.parseChords(hg);
        System.out.println(chords);
        player.play(chords);
    }
}
