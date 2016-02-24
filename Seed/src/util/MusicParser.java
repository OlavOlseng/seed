package util;

import genetics.ChordContainer;
import genetics.MelodyContainer;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by olavo on 2016-02-02.
 */
public class MusicParser {

    private Map<Integer, String> pitchMap;
    private Map<Integer, String> durationMap;

    private final static String SYMBOL_TIE = "-";
    private final static String SYMBOL_MEASURE = " | ";

    public MusicParser() {
        buildPitchMap();
        buildDurationMap();
    }

    private void buildPitchMap() {
        this.pitchMap = new HashMap<>();
        pitchMap.put(0, "C");
        pitchMap.put(1, "C#");
        pitchMap.put(2, "D");
        pitchMap.put(3, "D#");
        pitchMap.put(4, "E");
        pitchMap.put(5, "F");
        pitchMap.put(6, "F#");
        pitchMap.put(7, "G");
        pitchMap.put(8, "G#");
        pitchMap.put(9, "A");
        pitchMap.put(10, "A#");
        pitchMap.put(11, "B");
    }

    private void buildDurationMap() {
        this.durationMap = new HashMap<>();
        durationMap.put(1, "s");
        durationMap.put(2, "i");
        durationMap.put(3, "i.");
        durationMap.put(4, "q");
        durationMap.put(6, "q.");
        durationMap.put(8, "h");
        durationMap.put(12, "h.");
        durationMap.put(16, "w");
    }

    public String getPitchAsString(int value) {
        if (value == MelodyContainer.MELODY_REST) {
            return "R";
        }
        String pitch = pitchMap.get(value % 12);
        pitch += Integer.toString(value / 12);
        return pitch;
    }

    public String parseMelody(MelodyContainer container) {
        String melodyString = "";
        int noteStart = 0;
        int pitchValue = container.melody[0];
        boolean frontTied = false;
        boolean tailTied = false;
        int duration = 1;
        int i = 1;

        while (i < container.melody.length) {
            duration = i - noteStart;
            //Note done case, write the buffers
            if (container.melody[i] != MelodyContainer.MELODY_HOLD) {
                melodyString += buildNoteString(pitchValue, duration, frontTied, tailTied);
                frontTied = false;
                tailTied = false;
                pitchValue = container.melody[i];
                noteStart = i;
            }
            //New measure case, write buffer with proper ties.
            if (i % 16 == 0) {
                if(noteStart != i) {
                    tailTied = true;
                    melodyString += buildNoteString(pitchValue, duration, frontTied, tailTied);
                    frontTied = true;
                    tailTied = false;
                }
                melodyString += SYMBOL_MEASURE;
                noteStart = i;
            }
            i++;
        }
        melodyString += buildNoteString(pitchValue, duration + 1, frontTied, tailTied);
        return melodyString;
    }

    public String buildNoteString(int pitchValue, int duration, boolean tiedFromFront, boolean tiedFromTail) {
        String s = "";
        int splittingPoint = getNoteTiePartition(duration);
        if (splittingPoint != 0) {
            tiedFromTail = true;
            s += buildNoteString(pitchValue,splittingPoint,tiedFromFront, tiedFromTail);
            duration -= splittingPoint;
            tiedFromFront = true;
            tiedFromTail = false;
        }
         s += getPitchAsString(pitchValue);
        if (tiedFromFront) {
            s += SYMBOL_TIE;
        }
        s += durationMap.get(duration);
        if (tiedFromTail) {
            s += SYMBOL_TIE;
        }
        s += " ";
        return s;
    }

    private int getNoteTiePartition(int duration) {
        //Needs buffering, since duration can't be produced.
        if (durationMap.get(duration) == null) {
            int i = duration - 1;
            while(i > 0) {
                int secondPart = duration - i;
                if (durationMap.get(i) != null && durationMap.get(secondPart) != null) {
                    return i;
                }
                i--;
            }
        }

        /** Note crosses fourths, and should be paritioned, unless it is a whole, half or punctured halfnote.
         noteStart = noteStart % 16;
         if ((noteStart + duration) % 4 != 0) {
            if (duration < 4) {

            }
        }
        **/

        return 0;
    }

    /**
     * Creates a naive chord progression, no inversions are made.
     * @param hg
     * @return A string representing the chords in JFugue.
     */
    public String parseChords(ChordContainer hg) {
        String chords = "";
        for (byte[] chord : hg.chords) {
            int baseOctave = hg.BASE_OCTAVE;
            int lastPitch = -2;
            for (int i = 0; i < chord.length - 1; i++) {
                int currentPitch = (int)chord[i];
                if(currentPitch <= lastPitch) {
                    baseOctave++;
                }
                chords += pitchMap.get(currentPitch) + "" + baseOctave + "w";
                lastPitch = currentPitch;
                if(i != chord.length - 2) {
                    chords += "+";
                }
            }
            int currentPitch = chord[chord.length - 1];
            if(currentPitch <= lastPitch) {
                baseOctave++;
            }
            if (currentPitch != -1) {
                chords += "+" + pitchMap.get(currentPitch) + baseOctave + "w";
            }
            chords += " | ";
        }
        return chords;
    }

    public static void main(String[] args) {
        MusicParser parser = new MusicParser();
        System.out.println(parser.getPitchAsString(0));
        System.out.println(parser.getPitchAsString(1));
        System.out.println(parser.getPitchAsString(13));
        System.out.println(parser.getPitchAsString(55));
        System.out.println(parser.getPitchAsString(60));
        System.out.println(parser.getPitchAsString(127));

        MelodyContainer mc = new MelodyContainer(8, new MusicalKey(0, MusicalKey.Mode.MAJOR));
        mc.init();
        mc.melody[0] = 60;
        mc.melody[4] = 62;
        mc.melody[8] = 64;
        mc.melody[12] = 65;
        mc.melody[16] = 67;
        mc.melody[24] = 67;
        mc.melody[32] = 69;
        mc.melody[36] = 69;
        mc.melody[40] = 69;
        mc.melody[44] = 69;
        mc.melody[48] = 67;
        mc.melody[64] = 65;
        mc.melody[68] = 65;
        mc.melody[72] = 65;
        mc.melody[76] = 65;
        mc.melody[80] = 64;
        mc.melody[88] = 64;
        mc.melody[96] = 62;
        mc.melody[100] = 62;
        mc.melody[104] = 62;
        mc.melody[108] = 62;
        mc.melody[112] = 60;

        MusicalKey key = new MusicalKey(0, MusicalKey.Mode.MAJOR);

        ChordContainer hg = new ChordContainer(8, key);
        hg.init();
        hg.chords[0] = ChordBuilder.getChord(0, 3, key);
        hg.chords[1] = ChordBuilder.getChord(0, 3, key);
        hg.chords[2] = ChordBuilder.getChord(3, 3, key);
        hg.chords[3] = ChordBuilder.getChord(0, 3, key);
        hg.chords[4] = ChordBuilder.getChord(1, 3, key);
        hg.chords[5] = ChordBuilder.getChord(5, 3, key);
        hg.chords[6] = ChordBuilder.getChord(4, 4, key);
        hg.chords[7] = ChordBuilder.getChord(0, 3, key);

     /**LISA GIKK TIL SKOLEN

        MelodyGenotype mc = new MelodyGenotype(2);
        mc.init();
        mc.melody[1] = 60;
        mc.melody[18] = 61;
         **/



        String melody = parser.parseMelody(mc);
        System.out.println(melody);
        melody = " Rw | " + melody;
        String chords = "Rw | " + parser.parseChords(hg);
        System.out.println(chords);
        Pattern pMelody = new Pattern(melody).setVoice(0);
        Pattern pHarmony = new Pattern(chords).setVoice(1);

        Player player = new Player();
        player.play(pMelody, pHarmony);
    }
}
