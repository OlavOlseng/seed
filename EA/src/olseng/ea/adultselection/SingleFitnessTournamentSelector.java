package olseng.ea.adultselection;

import olseng.ea.core.Population;
import olseng.ea.genetics.Phenotype;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Olav on 11.01.2016.
 */
public class SingleFitnessTournamentSelector extends AdultSelector {

    private int tournamentSize = 10;
    private double randomProbability = 0.1;

    /**
     * Initializes a TournamnetSelector with specified parameters.
     * @param tournamentSize - Size of the tournaments.
     * @param randomProbability - Chance that a random member of the population is chosen, instead of the most fit individual.
     */
    public SingleFitnessTournamentSelector(int tournamentSize, double randomProbability) {
        this.tournamentSize = tournamentSize;
        this.randomProbability = randomProbability;
    }

    public void setTournamentSize(int size) {
        this.tournamentSize = size;
    }

    public void setRandomProbability(double randomProbability) {
        this.randomProbability = randomProbability;
    }

    private List<Phenotype> buildTournament(Population population) {
        Random random = new Random();
        List<Phenotype> tournament = new ArrayList<>(tournamentSize);
        List<Integer> selected = new ArrayList<>(tournamentSize);

        int picked = 0;
        while(picked < tournamentSize) {
            int index =  (random.nextInt(population.getPopulationSize()));
            if (selected.contains(index)) {
                continue;
            }
            tournament.add(population.getIndividual(index));
            picked++;
        }
        return tournament;
    }

    @Override
    public Phenotype getIndividual(Random random) {
        List<Phenotype> tournament = buildTournament(this.population);

        if (random.nextDouble() > randomProbability) {
            Phenotype winner = tournament.get(0);

            for (int i = 1; i < tournament.size(); i++) {
                Phenotype opponent = tournament.get(i);
                if(winner.getFitnessValue(0) > opponent.getFitnessValue(0)) {
                    winner = opponent;
                }
            }
            return winner;
        }
        return tournament.get(random.nextInt(tournamentSize));
    }
}
