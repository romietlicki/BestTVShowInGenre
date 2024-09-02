package besttvshowingenre;

import java.io.IOException;



public class Main {

	public static void main(String[] args) throws IOException {
	
		
		String genre = "Animation";
		
	        String result = Teste.bestInGenre(genre);
	        
	        
//	       List<String> allTVSeries = Arrays.asList("Ricky and Morty","Avatar: The Last Airbender");
//			Collections.sort(allTVSeries, new Comparator<String>() {
//
//				@Override
//				public int compare(String o1, String o2) {
//					return o1.compareTo(o2);
//				}
//				
//			});
			
			 System.out.println(result);

	}


}
