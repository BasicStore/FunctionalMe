package app;
import java.util.Arrays;
import java.util.List;
import java.util.Spliterator;
import java.util.StringJoiner;
import java.util.stream.Collectors;


public class StringAndListUtilities {
	
	public StringAndListUtilities() {
		doSpliterator();
		doStringJoiner();
		doCollectorsJoining();
	}
	
	
	// iterates sequentially through a list in a single or multiple threads
	// .forEachRemaining = single threaded iteration
	// .tryAdvance() = multiple threaded iteration
	private void doSpliterator() {
		System.out.println("\nDeomonstrate spliterator: ");
		List<String> names = Arrays.asList("hickory", "dickory", "dock");
		Spliterator<String> namesSpliterator = names.spliterator();
		namesSpliterator.forEachRemaining(System.out::println);
	}
	
	
	private void doStringJoiner() {
		System.out.println("\nDomonstrate joiner:");
		StringJoiner sj = new StringJoiner(",");
        sj.add("aaa");
        sj.add("bbb");
        sj.add("ccc");
		System.out.println("Write the contents of a list sequentially into a string "
				+ "separated by the joiner argument ',': " + sj.toString());

		sj = new StringJoiner("-", "prefix-", "-suffix");
        sj.add("2016");
        sj.add("02");
        sj.add("26");
        System.out.println("" + sj.toString());
        System.out.println("Write the contents of a list sequentially into a string "
				+ "separated by the joiner argument '-', with the "
				+ "given prefix and suffix: " + sj.toString());
        
        // INTERNALLY, java runtime uses the String.join() class to implement the string joiner
        List<String> list = Arrays.asList("java", "python", "nodejs", "ruby");
    	String result = String.join(", ", list);
    	System.out.println("Onder the bonnet: " + result);
	}
	
	
	private void doCollectorsJoining() { 
		List<String> list = Arrays.asList("java", "python", "nodejs", "ruby");
		String result = list.stream().map(x -> x).collect(Collectors.joining(" | "));
		System.out.println("Collectors joining on same list: " + result);
		
		List<Game> games = Arrays.asList(
                new Game("Dragon Blaze", 5),
                new Game("Angry Bird", 5),
                new Game("Candy Crush", 5)
        );

        //{Dragon Blaze, Angry Bird, Candy Crush}
        result = games.stream().map(x -> x.getName())
			.collect(Collectors.joining(", ", "{", "}"));
        //                               split pref  suff     
        System.out.println("Joining to string on video games list: " + result);
	}
	
	
	class Game{
        String name;
        int ranking;

        public Game(String name, int ranking) {
            this.name = name;
            this.ranking = ranking;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getRanking() {
            return ranking;
        }

        public void setRanking(int ranking) {
            this.ranking = ranking;
        }
    }
	
	

}
