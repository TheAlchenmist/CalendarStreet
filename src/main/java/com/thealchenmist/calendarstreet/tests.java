import static org.junit.Assert.*;

import java.io.IOException;
import java.net.MalformedURLException;

import org.json.simple.parser.ParseException;

import com.sothawo.mapjfx.*;

import org.junit.Test;
import java.util.Date;

public class tests {

	@Test
	
	public void test() throws MalformedURLException, IOException, ParseException {
		Coordinate anneArundel = Event.geocode("Anne Arundel Hall,College Park,Maryland");
		System.out.println(anneArundel.getLatitude()+" "+anneArundel.getLongitude());
		assertEquals("", 38.98594005, anneArundel.getLatitude(), .0000001);
		assertEquals("", -76.94673185, anneArundel.getLongitude(), .0000001);
		
		Database.connectToDatabase();
		Database.createNewTable();
		Date newDate = new Date(0,0,0,0,0);
		Date oldDate = new Date(0,0,0,0,0);
		String search = "Prince Frederick, College Park, Maryland";
		Coordinate coor = Event.geocode(search);
		Database.insertEvent(newDate,oldDate, "hi","meh",search,coor);
		Database.insertEvent(newDate,oldDate, "hey","meh",search,coor);
		Database.getEvent();
		Database.updateEvent(4,newDate,oldDate, "hi","lol",search,coor);
		
	}
	

}
