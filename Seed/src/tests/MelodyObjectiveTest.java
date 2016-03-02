package tests;

import fitness.MelodyObjective;
import genetics.*;
import olseng.ea.EAFactory;
import olseng.ea.adultselection.TournamentSelector;
import olseng.ea.core.EA;
import olseng.ea.core.Population;
import olseng.ea.fitness.SingleFitnessComparator;
import olseng.ea.genetics.OperatorPool;
import olseng.ea.genetics.Phenotype;
import operators.crossover.SingleBarCrossover;
import operators.crossover.SinglePointCrossover;
import operators.melodic.NoteModeMutator;
import operators.melodic.NoteSwapMutator;
import operators.melodic.OctaveModulationMutator;
import operators.melodic.PitchModulationMutator;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;
import util.ChordBuilder;
import util.MusicParser;
import util.MusicalKey;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Olav on 02.03.2016.
 */
public class MelodyObjectiveTest {

    public static void main(String[] args) {
        OperatorPool<MusicGenotype> op = new OperatorPool<>();
        op.addOperator(new NoteModeMutator(1));
        op.addOperator(new NoteSwapMutator(1));
        //op.addOperator(new OctaveModulationMutator(0.5));
        op.addOperator(new PitchModulationMutator(1));
        op.addOperator(new SingleBarCrossover(1));
        op.addOperator(new SinglePointCrossover(1));
        op.setCrossoverProbability(0.2);

        EAFactory<MusicGenotype, MusicPhenotype> factory = new EAFactory<>();
        factory.addFitnessObjective(new MelodyObjective());
        factory.developmentalMethod = new MusicDevelopmentalMethod();
        factory.operatorPool = op;
        factory.adultSelector = new TournamentSelector(2, 0.3);
        factory.sortingModule = new SingleFitnessComparator(0);

        EA<MusicGenotype, MusicPhenotype> ea = factory.build();
        ea.setThreadCount(32);
        ea.populationMaxSize = 100;
        ea.allowMutationAndCrossover = true;

        MusicalKey key = new MusicalKey(0, MusicalKey.Mode.MAJOR);
        MusicalContainer music = new MusicalContainer(8, key);
        music.init();

        MelodyContainer mc = music.melodyContainer;
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

        ChordContainer hg = music.chordContainer;
        hg.init();
        hg.chords[0] = ChordBuilder.getChord(0, 3, 1, key);
        hg.chords[1] = ChordBuilder.getChord(0, 3, 1, key);
        hg.chords[2] = ChordBuilder.getChord(3, 3, 1, key);
        hg.chords[3] = ChordBuilder.getChord(0, 3, 1, key);
        hg.chords[4] = ChordBuilder.getChord(1, 3, 1, key);
        hg.chords[5] = ChordBuilder.getChord(5, 3, 1, key);
        hg.chords[6] = ChordBuilder.getChord(4, 4, 1, key);
        hg.chords[7] = ChordBuilder.getChord(0, 3, 1, key);


        MusicGenotype initialSeed = new MusicGenotype(music);
        List<Phenotype> initialPop = new ArrayList<>();
        MusicPhenotype p = ea.developmentalMethod.develop(initialSeed);
        ea.fitnessEvaluator.evaluate(p);
        System.out.println(p.getFitnessValue(0));
        initialPop.add(p);

        Population pop = new Population(10);
        pop.setPopulation(initialPop);

        ea.initialize(pop);

        for (int i = 0; i < 1000; i++) {
            ea.step();
        }
        System.out.println(ea.population.getIndividual(0).getRepresentation());
        System.out.println(ea.population.getIndividual(0).getFitnessValue(0));
        ea.terminateThreads();

        MusicParser parser = new MusicParser();
        music = (MusicalContainer) ea.population.getIndividual(0).getRepresentation();
        String melody = parser.parseMelody(music.melodyContainer);
        System.out.println(melody);
        melody = " Rw | " + melody;
        String chords = "Rw | " + parser.parseChords(music.chordContainer);
        System.out.println(chords);
        Pattern pMelody = new Pattern(melody).setVoice(0);
        Pattern pHarmony = new Pattern(chords).setVoice(1);

        Player player = new Player();
        player.play(pMelody, pHarmony);

    }

}
