package app;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FunctionalMain {

	public FunctionalMain() {
		// fundamental functional + java 8 concepts
		countItems(Arrays.asList("888", "999", "555", "777", "222", "444"));
		List<Integer> intList = Arrays.asList(55, 33, 77, 88, 55, 22);
		listContains(intList);
		sortLists(intList);
		certainSheepLists("curly");
		mapSheepToNameList();
		convertAnArrayOfOldSheep();
		reduceExampleAndGrouping();
		optionalUsage();
		otherStreamSyntaxAndFlatMappingToStreams();
		justInTime();
		// TODO: string joiner
				
		new MapLooper();
		new BadPass();
	}
	
/*
Practice basics:
- count list items
- contains
- sort list items
- looping with forEach 
- filter a list
- discussion on the usage of parallel streams 
- convert an array to a list & a collection to a list
- filter / convert a string of objects to a string list 	
- create a map from a list of elements
- cumulative operations with reduce: how to get the sum of a list 	
- look at class StatisticsUtility
- usage of Optional 	
- what is the difference between map and flat map? 	 	
- format the default local date time now
- create a specific local date and format it 	
- create a specific local date time and format it 	


- identify 6 ways to iterate through a map these days:
  https://www.codingame.com/playgrounds/6162/6-ways-to-iterate-or-loop-a-map-in-java
 	
*/
	
	
	private void otherStreamSyntaxAndFlatMappingToStreams() {
		System.out.print("\nInt list woz 'ere: \n");
		List<Integer> intList = Stream.of('1', '2', '3').map(ch -> Integer.parseInt(ch.toString())).collect(Collectors.toList());
		intList.forEach(n->System.out.print(" " + n));
		
		
		System.out.print("\nFLAT MAP: Flatten a stream of lists, to avoid having to loop through each list individually as in Java 7\n");
		Stream<List<Integer>> integerListStream = Stream.of(
				  Arrays.asList(1, 2), 
				  Arrays.asList(3, 4), 
				  Arrays.asList(5)
				);
		Stream<Integer> integerStream = integerListStream.flatMap(Collection::stream);
		integerStream.forEach(n->System.out.print(" " + n));
	}

	
	
	// OPTIONAL:  remember to use Optional.ofNullable(Sheep); + sheep.ifPresent(Consumer - some void act)
	//            Optional.of() will throw a NullPointer if the argument is null
	private void optionalUsage() {
		Optional<Sheep> sheep = getRandomSheep();
		sheep.ifPresent(s-> System.out.println("\n\nSelected sheep: " + s.getName()));
		getRandomSheep().ifPresent(s-> System.out.println("\n\n2nd selection: " + s.getName()));
		
		// this is of limited use
		String nullName = null;
	    String name = Optional.ofNullable(nullName).orElse("john");
	}
	
		
	// note mapToInt() maps to an IntStream, and from that get SummaryStatistics for the integer
	// SummaryStatistics.getCount(); summary.getSum(); summary.getMax(); summary.getMin();
	private void reduceExampleAndGrouping() {
		Sheep[] sheepArr = new Sheep[] {new Sheep(1, "black", "fine", "Mildy", 88), new Sheep(8, "blue", "steely", "Gertrude", 54),
				new Sheep(2, "white", "curly", "Tolly", 101), new Sheep(3, "blue", "straight", "Savvy", 333)};
		int totalFlockAge = Arrays.stream(sheepArr).mapToInt(Sheep::getAge).sum();
		System.out.println("\nTotal flock age = " + totalFlockAge + ", a bit on the high side");
		
		// give 1 bonus year for every sheep
		totalFlockAge = Arrays.stream(sheepArr).map(Sheep::getAge).reduce(0, (a, b)-> a + b + 1);
		System.out.println("\nTotal flock age with bonus years = " + totalFlockAge + " ==> should be 4 more");				   
		
		int totalEmptyFlockAge = new ArrayList<Sheep>().stream().map(Sheep::getAge).reduce(0, (a, b)-> a + b);
		System.out.println("\nTotal flock age where there are no sheep = " + totalEmptyFlockAge + " ==> should use identity which is ZERO");
		
		IntSummaryStatistics summary = Arrays.stream(sheepArr).mapToInt(Sheep::getAge).summaryStatistics();
		System.out.println("\nAverage flock age including bonus year = " + Math.round(summary.getAverage()));

		System.out.print("\nCreating gendered sheep......");
		List<Sheep> genderedSheep = Arrays.asList(new Sheep[] {new Sheep(1, "black", "fine", "Mildy", 88, Gender.FEMALE), new Sheep(8, "blue", "steely", "Gertrude", 54, Gender.FEMALE),
				new Sheep(2, "white", "curly", "Tolly", 101, Gender.MALE), new Sheep(3, "blue", "straight", "Savvy", 333, Gender.MALE)});
		Map<Gender, List<String>> genderedSheepMap = genderedSheep.parallelStream().collect(Collectors.groupingBy(
                       Sheep::getGender, Collectors.mapping(Sheep::getName, Collectors.toList()
                    		   )));

		System.out.print("\nGroup sheep by gender:");
		for (Map.Entry<Gender, List<String>> entry : genderedSheepMap.entrySet()) {
			System.out.print("\n-->" + entry.getKey() + ": ");// + " value : " + entry.getValue());
			entry.getValue().forEach(s->System.out.print(" " + s));
	    }
	}
	
	
	private void convertAnArrayOfOldSheep() {
		System.out.print("\nConvert an array of old sheep: ");
		Sheep[] sheepArr = new Sheep[] {new Sheep(1, "black", "fine", "Mildy"), new Sheep(8, "blue", "steely", "Gertrude"),
				new Sheep(2, "white", "curly", "Tolly"), new Sheep(3, "blue", "straight", "Savvy")}; 
		List<Sheep> oldSheep = Arrays.stream(sheepArr).collect(Collectors.toList());
		oldSheep.forEach(s->System.out.print(" " + s.getName()));
		
		// example only: convert to a set and assign to a collection
		Collection<Sheep> sheepCollection = oldSheep.stream().collect(Collectors.toSet()); 
		
		// example only: convert from a Collection to a map
		Map<String, String> nameToColourMap = 
				sheepCollection.parallelStream().collect(Collectors.toMap(s->s.getName(), s->s.getColour()));
		
		System.out.print("\nAll unique sheep colours = ");
		Collection<String> sheepColours = nameToColourMap.values().stream().collect(Collectors.toSet());
		sheepColours.forEach(s->System.out.print(" " + s));
	}
	
	
	
	private void mapSheepToNameList() {
		System.out.print("Printing out the sheep names: \n");
		List<Sheep> sheepList = Arrays.asList(new Sheep(1, "black", "fine", "Mildy"), 
				new Sheep(2, "white", "curly", "Tolly"), new Sheep(3, "blue", "straight", "Savvy"));
		List<String> sheepNameList = sheepList.parallelStream()
				.filter(o->o.getColour().equals("blue"))
				.map(o->o.getName()).collect(Collectors.toList());
		sheepNameList.forEach(v->System.out.print(" " + v));
	}
	
	
	private void certainSheepLists(String woolType) {
		List<Sheep> sheepList = Arrays.asList(new Sheep(1, "black", "fine", "Mildy"), 
				new Sheep(2, "white", "curly", "Tolly"), new Sheep(3, "blue", "straight", "Savvy"));

		System.out.print("\nPrint out names with wool type: "+ woolType + "\n====> ");
		List<Sheep> filteredSheepList = sheepList.stream().filter(p-> p.getWoolType().equals("curly")).collect(Collectors.toList());
		filteredSheepList.forEach(v->System.out.print(" " + v.getName()));
		
		System.out.print("\nPrint out names with wool type: fine\n====> ");
		List<Sheep> filteredSheepList2 = sheepList.stream().filter(p-> p.getWoolType().equals("fine")).collect(Collectors.toList());
		filteredSheepList2.forEach(v->System.out.print(" " + v.getName()));
	}
	
	
	
	private void sortLists(List<Integer> intList) {
		System.out.print("Print out the int list sorted from lowest to highest\n====> ");
		intList.sort((a, b)-> a.compareTo(b));
		intList.forEach(v->System.out.print(" " + v.toString()));
		
		System.out.print("Print out the int list sorted from highest to lowest\n====> ");
		intList.sort((a, b)-> b.compareTo(a));
		intList.forEach(v->System.out.print(" " + v.toString()));
		
		List<Sheep> sheepList = Arrays.asList(new Sheep(1, "black", "fine"), 
				new Sheep(2, "white", "curly"), new Sheep(3, "blue", "straight"));

		System.out.print("Print out sheep in order of colour\n====> ");
		sheepList.sort((a, b)->a.getColour().compareTo(b.getColour()));
		sheepList.forEach(v->System.out.print(" " + v.getColour().toString()));
				
		System.out.print("\nWhat happens when there are null values?\n====> ");
		sheepList = Arrays.asList(new Sheep(1, "black", "fine"), 
				new Sheep(2, "white", "curly"), new Sheep(3, "blue", "straight"), new Sheep(4, null, null));  
		sheepList.sort((a, b)->{ // check against the object too
			if (a.getColour() == null && b.getColour() == null) {
				return 0; 
			} else if (a.getColour() == null) {
				return -1;
			} else if (b.getColour() == null) {
				return 1;
			}
			return a.getColour().compareTo(b.getColour());
		});
		sheepList.forEach(v->System.out.print(v.getColour() != null ? " " + v.getColour().toString() : "[no colour]"));
	}
	
	
	/* 
	    PARALLEL STREAMS divide the provided task into many and run them in different threads, 
	    utilizing multiple cores of the computer. On the other hand sequential streams work 
	    just like a for loop using a single thread on a single core processor.	
	    
	    Parallelization was the main driving force behind the switch to functional programming (lmmbdas, stream api, method references etc).
	    But there is a problem:
	    --> All parallel streams use the Common fork-join thread pool 
	        - if a thread gets stuck or slowed down on this one threadpool, then fast threads might get stuck
	          behind one which is severely help up.
	          As there is no chance to specify your own threadpool, either be sure that no tasks can get help up, 
	          or do not use parallel streams.
	          
	          --> I guess this is an opinion only, surely it cannot be any worse than single thread streaming 
	     
	    Common fork-join thread pool is a single threadpool used by the JVM  
	    where idle threads can do some of the work of other tasks being carried out by other threads 
	 */
	private void listContains(List<Integer> intList) {
		int val1 = 77;
		boolean containsVal1 = intList.parallelStream().filter(n -> n==val1).findFirst().isPresent();
		int val2 = 56;
		boolean containsVal2 = intList.parallelStream().filter(n -> n==val2).findFirst().isPresent();
		
		System.out.println("List contains val1: " + containsVal1);
		System.out.println("List contains val2: " + containsVal2);
	}
	
	
	private void countItems(List<String> strList) {
		System.out.println("Counting items - number = " + strList.stream().count());
	}
	
	
    public static void main(String[] args) {
		new FunctionalMain();
	}
       
    
    private Optional<Sheep> getRandomSheep() {
    	List<Sheep> sheepList = Arrays.asList(new Sheep(1, "black", "fine", "Mildy", 88), new Sheep(8, "blue", "steely", "Gertrude", 54),
				new Sheep(2, "white", "curly", "Tolly", 101), new Sheep(3, "blue", "straight", "Savvy", 333));
    	int sheepSelector = (int)(Math.random() * 4); 
    	
    	// obviously this is artificial.........sheep would never be null here....but
    	return Optional.ofNullable(sheepList.get(sheepSelector));
    }
        
    
    private Optional<Sheep> getMissingSheep() {
    	return Optional.ofNullable(null);
    }
    
    
  
    public enum Gender {
    	MALE,
    	FEMALE;
    }
    
    
    public class Sheep {
    	
    	private int id;
    	private String colour;
    	private String woolType;
    	private String name;
    	private int age;
    	private Gender gender;    	
		
    	public Sheep(int id, String colour, String woolType) {
			this.id = id;
			this.colour = colour;
			this.woolType = woolType;
		}

    	public Sheep(int id, String colour, String woolType, String name) {
			this(id, colour, woolType);
			this.name = name;
		}
    	
    	public Sheep(int id, String colour, String woolType, String name, int age) {
			this(id, colour, woolType, name);
			this.age = age;
		}
    	
    	public Sheep(int id, String colour, String woolType, String name, int age, Gender gender) {
			this(id, colour, woolType, name, age);
			this.gender = gender;
		}
    	
		public int getId() {
			return id;
		}

		public String getColour() {
			return colour;
		}

		public String getWoolType() {
			return woolType;
		}

		public String getName() {
			return name;
		}

		public int getAge() {
			return age;
		}

		public Gender getGender() {
			return gender;
		}
		
    }
   
    
    
    
    // Advantages:  1) consistent parameters y/m/d  
    //              2) made immutable so are threadsafe (ie. another thread cannot change the value of a date) 
    //              3) locale simplified with ZoneId
    //              4) allows thread chaining, with methods returning the updated time
    /*
     			ZonedDateTime nextFriday = LocalDateTime.now()
  												.plusHours(1)
  												.with(TemporalAdjusters.next(DayOfWeek.FRIDAY))
  												.atZone(ZoneId.of("PST"));
     */
    // List of regions: https://docs.oracle.com/javase/8/docs/api/java/time/ZoneId.html
    //                  it seems the exact country can be specified (certainly within Europe)
    private void justInTime() {
    	DateTimeFormatter trustyFormatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd HH:mm:ss"); 
    	DateTimeFormatter miniFormatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
    	
    	//ZonedDateTime krTime = LocalDateTime.now().atZone(ZoneId.of("Europe/Berlin"));
    	ZonedDateTime krTime = LocalDateTime.now().atZone(ZoneId.of("Europe/Berlin"));
    	System.out.println("\n\nTime in Berlin: " + trustyFormatter.format(krTime));

    	LocalDateTime localtime = LocalDateTime.now(); 
    	System.out.println("Time here in Blighty: " + trustyFormatter.format(localtime));
    	
    	LocalDate localDate = LocalDate.now(); 
    	System.out.println("Current date: " + miniFormatter.format(localDate));
    	
    	LocalDate armyGolf = LocalDate.of(1987, 8, 21);
    	System.out.println("Current date: " + miniFormatter.format(armyGolf));
    	
    	System.out.println("Result of time operations:");
    	LocalDate date = LocalDate.parse("2018-02-13").plusDays(5);
    	System.out.println("Current date: " + miniFormatter.format(date));
    	date = LocalDate.parse("2018-02-13").plus(3, ChronoUnit.MONTHS);
    	System.out.println("Current date: " + miniFormatter.format(date));
    	
    	LocalDateTime specificTime = LocalDateTime.of(1987, 8, 21, 18, 30, 12);
    	System.out.println("Specific time: " + trustyFormatter.format(specificTime));
    }
        
	
}
















