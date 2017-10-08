import static org.junit.Assert.*;

import java.io.IOException;
import java.net.MalformedURLException;

import org.json.simple.parser.ParseException;

import com.sothawo.mapjfx.*;

import org.junit.Test;

public class tests {

	@Test
	public void test() throws MalformedURLException, IOException, ParseException {
		Coordinate anneArundel = Event.geocode("Anne Arundel Hall,College Park,Maryland");
		System.out.println(anneArundel.getLatitude()+" "+anneArundel.getLongitude());
		assertEquals("", 38.98594005, anneArundel.getLatitude(), .0000001);
		assertEquals("", -76.94673185, anneArundel.getLongitude(), .0000001);
		
		Database.connectToDatabase();
	}
	

}
