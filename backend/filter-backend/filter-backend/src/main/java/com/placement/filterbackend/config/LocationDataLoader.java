package com.placement.filterbackend.config;

import com.placement.filterbackend.entity.Location;
import com.placement.filterbackend.repository.LocationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Order(3) 
public class LocationDataLoader implements CommandLineRunner {

    private final LocationRepository locationRepository;

    public LocationDataLoader(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public void run(String... args) throws Exception {
      
        if (locationRepository.count() == 0) {
            List<Location> locations = Arrays.asList(
                    
                    createLocation("Bengaluru"),
                    createLocation("Hyderabad"),
                    createLocation("Pune"),
                    createLocation("Chennai"),
                    createLocation("Mumbai"),
                    createLocation("Delhi NCR"),
                    createLocation("Gurugram"),
                    createLocation("Noida"),
                    createLocation("Kolkata"),
                    createLocation("Ahmedabad"),
                    createLocation("Jaipur"),
                    createLocation("Chandigarh"),
                    createLocation("Indore"),
                    createLocation("Thiruvananthapuram"),
                    createLocation("Coimbatore"),
                    createLocation("Mysuru"),
                    createLocation("Nagpur"),
                    createLocation("Visakhapatnam"),
                    createLocation("Bhubaneswar"),
                    createLocation("Lucknow"),

                    createLocation("Kochi"),
                    createLocation("Mangalore"),
                    createLocation("Madurai"),
                    createLocation("Tiruchirappalli"),
                    createLocation("Vijayawada"),
                    createLocation("Warangal"),
                    createLocation("Guwahati"),
                    createLocation("Dehradun"),
                    createLocation("Bhopal"),
                    createLocation("Raipur"),
                    createLocation("Patna"),
                    createLocation("Ranchi"),
                    createLocation("Jamshedpur"),
                    createLocation("Kolhapur"),
                    createLocation("Belagavi"),
                    createLocation("Hubballi"),
                    createLocation("Dharwad"),
                    createLocation("Nashik"),
                    createLocation("Aurangabad"),
                    createLocation("Vadodara"),
                    createLocation("Surat"),
                    createLocation("Gandhinagar"),
                    createLocation("Rajkot"),
                    createLocation("Udaipur"),
                    createLocation("Jodhpur"),
                    createLocation("Kota"),
                    createLocation("Amritsar"),
                    createLocation("Ludhiana"),
                    createLocation("Jalandhar"),
                    createLocation("Mohali"),
                    createLocation("Panchkula"),
                    createLocation("Meerut"),
                    createLocation("Kanpur"),
                    createLocation("Varanasi"),
                    createLocation("Prayagraj"),
                    createLocation("Agra"),
                    createLocation("Gwalior"),
                    createLocation("Jabalpur"),

                    
                    createLocation("San Francisco Bay Area"),
                    createLocation("Seattle"),
                    createLocation("New York City"),
                    createLocation("Boston"),
                    createLocation("Austin"),
                    createLocation("Los Angeles"),
                    createLocation("Chicago"),
                    createLocation("Toronto"),
                    createLocation("Vancouver"),
                    createLocation("London"),
                    createLocation("Cambridge (UK)"),
                    createLocation("Berlin"),
                    createLocation("Munich"),
                    createLocation("Paris"),
                    createLocation("Amsterdam"),
                    createLocation("Stockholm"),
                    createLocation("Dublin"),
                    createLocation("Zurich"),
                    createLocation("Singapore"),
                    createLocation("Tokyo"),
                    createLocation("Osaka"),
                    createLocation("Seoul"),
                    createLocation("Beijing"),
                    createLocation("Shanghai"),
                    createLocation("Shenzhen"),
                    createLocation("Hong Kong"),
                    createLocation("Taipei"),
                    createLocation("Sydney"),
                    createLocation("Melbourne"),
                    createLocation("Tel Aviv"),
                    createLocation("Dubai"),
                    createLocation("Moscow"),
                    createLocation("São Paulo"),
                    createLocation("Mexico City"),
                    createLocation("Cape Town"),
                    createLocation("Nairobi"));

            locationRepository.saveAll(locations);
            System.out.println("✅ Inserted " + locations.size() + " default locations.");
        } else {
            System.out.println("ℹ️ Locations table already contains data. Skipping insertion.");
        }
    }

    private Location createLocation(String name) {
        return Location.builder().name(name).build();
    }
}