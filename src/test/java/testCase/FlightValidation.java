package testCase;

import java.io.IOException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;



import PageObject.BookFlight;
import driver.DataaProvider;
import driver.Driver;

public class FlightValidation extends Driver {
	// public WebDriver driver;
	public Object[][] data;

	@Test(dataProvider = "Data")
	public void flightvalidate(String rowNumber, String SourceKey, String Sourcestation, String DestinationKey, String Destinationstation,
			String Date, String FlightName,String status, String flightFoundList) throws InterruptedException, IOException {

		BookFlight flights = new BookFlight(driver);
		flights.enterSource(SourceKey, Sourcestation);
		
		flights.enterDestination(DestinationKey, Destinationstation);
		flights.enterdate(Date);
		flights.clicksearch();

		flights.findFlights(FlightName, rowNumber);		
	}

	@DataProvider(name = "Data")
	public Object[][] runData() throws IOException {	 
		data = DataaProvider.getData();
		return data;
	}
}
