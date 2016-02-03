package tests;

import genetics.MusicGenotype;
import olseng.ea.genetics.OperatorPool;
import operators.NoteModeMutator;
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
        op.addOperator(new NoteModeMutator(1));
        op.addOperator(new PitchSwapMutator(1));
        Random rand = new Random();
        System.out.println(Arrays.toString(mc.melody));
        for (int i = 0; i < 400; i++) {
                mg = op.getMutationOperator().mutate(mg, rand);
            System.out.println("Iteration: " + i + " " + Arrays.toString(mg.getData().melody));
        }
        String music = "Rw | ";
        MusicParser parser = new MusicParser();
        music += parser.parseMelody(mg.getData());
        Player player = new Player();
        System.out.println(music);
        player.play(music);
    }
}
