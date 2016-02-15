package tests;

import genetics.MusicGenotype;
import genetics.MelodyGenotype;
import genetics.MusicalContainer;
import olseng.ea.genetics.OperatorPool;
import operators.NoteModeMutator;
import operators.PitchModulationMutator;
import operators.NoteSwapMutator;
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
        MelodyGenotype mc = ms.mg;
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
            //System.out.println("Iteration: " + i + " " + Arrays.toString(mg.getData().melody));
        }
        System.out.println(Arrays.toString(mg.getData().mg.melody));
        String melody = "Rw | ";
        MusicParser parser = new MusicParser();
        melody += parser.parseMelody(mg.getData().mg);

        int[] freqs = new int[MelodyGenotype.MELODY_RANGE + 1];
        for (int i = 0; i < mc.melody.length; i++) {
            if (mg.getData().mg.melody[i] >= MelodyGenotype.MELODY_RANGE_MIN) {
                freqs[mg.getData().mg.melody[i] - MelodyGenotype.MELODY_RANGE_MIN] += 1;
            }
        }

        String chords = "Rw | " + parser.parseChords(ms.hg);

        System.out.println("\nFrequencies: " + Arrays.toString(freqs) + "\n");
        System.out.println(melody);
        System.out.println(chords);

        Pattern p1 = new Pattern(melody).setVoice(1);
        Pattern p2 = new Pattern("Rw | " + parser.parseChords(ms.hg)).setVoice(2);
        Player player = new Player();
        player.play(p1, p2);
    }
}
