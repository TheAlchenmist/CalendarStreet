import static org.junit.Assert.*;

import java.io.IOException;
import java.net.MalformedURLException;

import org.json.simple.parser.ParseException;

import com.sothawo.mapjfx.*;

import org.junit.Test;

public class tests {

	@Test
	public void test() throws MalformedURLException, IOException, ParseException {
		Coordinate anneArundel = Event.geocode("Anne%20Arundel%20Hall,%20College%20Park,%20Maryland");
		assertTrue(anneArundel == new Coordinate(38.98594005,-76.94673185));
	}

}
