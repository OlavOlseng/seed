import olseng.ea.genetics.GeneticMutationOperator;
import org.jfugue.player.Player;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by Olav on 02.02.2016.
 */
public class PitchSwapMutator extends GeneticMutationOperator<MusicGenotype> {


    public PitchSwapMutator(double weight) {
        super(weight);
    }

    @Override
    public MusicGenotype mutate(MusicGenotype parent, Random rand) {
        MusicalContainer mc = parent.getDeepCopy();
        int index = rand.nextInt(mc.melody.length);
        int selectedNoteIndex = mc.getNoteStartIndex(index);
        int swapNote = mc.getNextNoteIndex(selectedNoteIndex);
        int previousNote = mc.getPreviousNoteIndex(selectedNoteIndex);
        MusicGenotype child = new MusicGenotype();

        if (swapNote < 0 || (rand.nextDouble() < 0.5 && previousNote > -1)) {
            swapNote = previousNote;
        }
        if (swapNote < 0) {
            System.out.println("Failed to swap notes, as there is less than two notes in the genotype.");
            child.setData(mc);
            return child;
        }
        byte buffer = mc.melody[selectedNoteIndex];
        mc.melody[selectedNoteIndex] = mc.melody[swapNote];
        mc.melody[swapNote] = buffer;

        //Ensure no consecutive pauses.
        mc.concatenateRests();

        child.setData(mc);
        return child;
    }

    public static void main(String[] args) {
        MusicalContainer mc = new MusicalContainer(2);
        mc.init();
        MusicGenotype mg = new MusicGenotype();
        mg.setData(mc);
        NoteModeMutator nmm = new NoteModeMutator(1);
        PitchSwapMutator psm = new PitchSwapMutator(1);
        Random rand = new Random();
        System.out.println(Arrays.toString(mc.melody));
        for (int i = 0; i < 100; i++) {
            if (i % 2 == 0) {
                mg = nmm.mutate(mg, rand);
            }
            else {
                mg = psm.mutate(mg, rand);
            }
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
