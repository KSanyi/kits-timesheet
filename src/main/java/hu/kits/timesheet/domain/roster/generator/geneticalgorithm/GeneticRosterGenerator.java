package hu.kits.timesheet.domain.roster.generator.geneticalgorithm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import hu.kits.timesheet.domain.roster.Employee;
import hu.kits.timesheet.domain.roster.OpeningHoursCalendar;
import hu.kits.timesheet.domain.roster.Roster;
import hu.kits.timesheet.domain.roster.generator.RosterGenerator;

public class GeneticRosterGenerator implements RosterGenerator {

	private final Random random = new Random();
	
	@Override
	public Roster generateRoster(OpeningHoursCalendar calendar, List<Employee> employees) {
		
		List<RosterGene> population = IntStream.range(1, 1000)
				.mapToObj(i -> RosterGene.createRandom(calendar, employees)).collect(Collectors.toList());
		
		for(int i=0;i<100;i++) {
			population = challengePopulation(population);
			mutatePopulation(population);
			population = doCrossOver(population);
		}
		
		return null;
	}
	
	private List<RosterGene> challengePopulation(List<RosterGene> population) {
		
		List<RosterGene> sortedPopulation = population.stream()
				.sorted(Comparator.comparing(RosterGene::fitness).reversed())
				.collect(Collectors.toList());
		
		return sortedPopulation.subList(0, sortedPopulation.size() / 2);
	}
	
	private void mutatePopulation(List<RosterGene> population) {
		
		population.stream().filter(g -> random.nextInt(10) == 0).forEach(RosterGene::mutate);
	}
	
	private List<RosterGene> doCrossOver(List<RosterGene> population) {
		
		List<RosterGene> newPopulation = new ArrayList<>();
		
		for(RosterGene gene : population) {
			RosterGene mate = randomGene(population);
			RosterGene newGene = gene.doCrossOver(mate);
			newPopulation.add(newGene);
		}
		
		newPopulation.addAll(population);
		
		return newPopulation;
	}

	private RosterGene randomGene(List<RosterGene> population) {
		
		return population.get(random.nextInt(population.size()));
		
	}
	
}
