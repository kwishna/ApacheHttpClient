import java.util.*;
import java.util.stream.Collectors;

public class Rough {
	public static void main(String[] args) {
		String s = "My Name Is Anthony Gonsalves";
		// 		System.out.println(new StringBuilder(s).reverse().toString());
		Character[] c1 = s.chars().mapToObj(c -> (char) c).toArray(Character[]::new);
		Arrays.sort(c1, Comparator.naturalOrder());
		System.out.println(s.chars().mapToObj(c -> (char) c).collect(Collectors.toSet()));
	}
}
