package util;


import genetics.MusicalContainer;
import org.jfugue.midi.MidiFileManager;
import org.jfugue.pattern.Pattern;

import java.io.File;
import java.io.IOException;

/**
 * Created by Olav on 07.05.2016.
 */
public class MidiWriter {

    public static void WritePatternToMidi(MusicalContainer music, String name) {
        try {
            MusicParser parser = new MusicParser();
            String melody = parser.parseMelody(music.melodyContainer);
            String chords = parser.parseChords(music.chordContainer);
            MidiFileManager.savePatternToMidi(new Pattern(melody), new File(name + "M.midi"));
            MidiFileManager.savePatternToMidi(new Pattern(chords), new File(name + "C.midi"));

        } catch (IOException e) {
            System.out.println("Failed to write to file!!");
            e.printStackTrace();
        }
    }

}
