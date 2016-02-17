package tests;

import genetics.MusicGenotype;
import genetics.MelodyGenotype;
import genetics.MusicalContainer;
import olseng.ea.genetics.OperatorPool;
import operators.melodic.NoteModeMutator;
import operators.melodic.PitchModulationMutator;
import operators.melodic.NoteSwapMutator;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;
import util.MusicParser;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by Olav on 03.02.2016.
 */
public class MelodyMutatorTest {

    public static void main(String[] args) {
        MusicalContainer ms = new MusicalContainer(8);
        MelodyGenotype mc = ms.melodyGenotype;
        mc.init();
        ms.randomize(new Random());
        genetics.MusicGenotype mg = new genetics.MusicGenotype();

        mg.setData(ms);

        OperatorPool<MusicGenotype> op = new OperatorPool<>();
        op.addOperator(new NoteModeMutator(0.5));
        op.addOperator(new NoteSwapMutator(1));
        op.addOperator(new PitchModulationMutator(4));
        op.normalizeWeights();

        Random rand = new Random();
        for (int i = 0; i < 1000; i++) {
            mg = op.getMutationOperator(rand).mutate(mg, rand);
            //System.out.println("Iteration: " + i + " " + Arrays.toString(melodyGenotype.getData().melody));
        }
        System.out.println(Arrays.toString(mg.getData().melodyGenotype.melody));
        String melody = "Rw | ";
        MusicParser parser = new MusicParser();
        melody += parser.parseMelody(mg.getData().melodyGenotype);

        int[] freqs = new int[MelodyGenotype.MELODY_RANGE + 1];
        for (int i = 0; i < mc.melody.length; i++) {
            if (mg.getData().melodyGenotype.melody[i] >= MelodyGenotype.MELODY_RANGE_MIN) {
                freqs[mg.getData().melodyGenotype.melody[i] - MelodyGenotype.MELODY_RANGE_MIN] += 1;
            }
        }

        String chords = "Rw | " + parser.parseChords(ms.harmonyGenotype);

        System.out.println("\nFrequencies: " + Arrays.toString(freqs) + "\n");
        System.out.println(melody);
        System.out.println(chords);

        Pattern p1 = new Pattern(melody).setVoice(1);
        Pattern p2 = new Pattern("Rw | " + parser.parseChords(ms.harmonyGenotype)).setVoice(2);
        Player player = new Player();
        player.play(p1, p2);
    }
}
