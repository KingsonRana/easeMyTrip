package DataProviders;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataProvider {
    private final Faker faker = new Faker(new Locale("en-IN"));
    @org.testng.annotations.DataProvider(name = "userCredentials")
    public Object[][] getUserAccountCredentials(){
       return new Object[][]{
               {"91","9864747463","dummy@gmail.com"}
       };
    }
    @org.testng.annotations.DataProvider(name="sourceAndDestination")
    public Object[][] getSourceAndDestinations() {
        LocalDate currentDate = LocalDate.now();
        String formattedDate = currentDate.plusDays(2).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        return new Object[][]{
                {"Delhi", "Patna", formattedDate}
        };
    }
    @org.testng.annotations.DataProvider(name="travellerDetails")
    public Object[][] getTravellerDetails() {
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        return new Object[][]{
                {firstName, lastName}
        };
    }
}
