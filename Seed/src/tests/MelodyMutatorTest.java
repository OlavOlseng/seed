package tests;

import genetics.MusicGenotype;
import genetics.MusicalContainer;
import olseng.ea.genetics.OperatorPool;
import operators.NoteModeMutator;
import operators.PitchModulationMutator;
import operators.PitchSwapMutator;
import org.jfugue.player.Player;
import util.MusicParser;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by Olav on 03.02.2016.
 */
public class MelodyMutatorTest {

    public static void main(String[] args) {
        genetics.MusicalContainer mc = new genetics.MusicalContainer(8);
        mc.init();
        genetics.MusicGenotype mg = new genetics.MusicGenotype();
        mg.setData(mc);

        OperatorPool<MusicGenotype> op = new OperatorPool<>();
        op.addOperator(new NoteModeMutator(0.5));
        op.addOperator(new PitchSwapMutator(1));
        op.addOperator(new PitchModulationMutator(4));
        op.normalizeWeights();

        Random rand = new Random();
        for (int i = 0; i < 1000; i++) {
            mg = op.getMutationOperator(rand).mutate(mg, rand);
            //System.out.println("Iteration: " + i + " " + Arrays.toString(mg.getData().melody));
        }
        System.out.println(Arrays.toString(mg.getData().melody));
        String music = "Rw | ";
        MusicParser parser = new MusicParser();
        music += parser.parseMelody(mg.getData());

        int[] freqs = new int[MusicalContainer.MELODY_RANGE + 1];
        for (int i = 0; i < mc.melody.length; i++) {
            if (mg.getData().melody[i] >= MusicalContainer.MELODY_RANGE_MIN) {
                freqs[mg.getData().melody[i] - MusicalContainer.MELODY_RANGE_MIN] += 1;
            }
        }
        System.out.println("\nFrequencies: " + Arrays.toString(freqs) + "\n");
        System.out.println(music);
        Player player = new Player();
        player.play(music);
    }
}
