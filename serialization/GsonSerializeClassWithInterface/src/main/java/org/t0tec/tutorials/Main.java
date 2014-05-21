package org.t0tec.tutorials;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.t0tec.tutorials.model.Car;
import org.t0tec.tutorials.model.CarHandling;
import org.t0tec.tutorials.model.CarInterfaceAdapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class Main {
	private static final Logger logger = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {
		Gson gson = new GsonBuilder().registerTypeAdapter(Car.class, new CarInterfaceAdapter()).create();

		ArrayList<Car> cars = new ArrayList<Car>();

		cars.add(new Car(new CarHandling(344, -1032, 0.38), "cars/white_f1.png"));
		cars.add(new Car(new CarHandling(333, -999, 0.35),  "cars/silver_f1.png"));
		cars.add(new Car(new CarHandling(366, -1098, 0.30), "cars/cyan_f1.png"));
		cars.add(new Car(new CarHandling(299, -897, 0.55),  "cars/black_f1.png"));

		cars.add(new Car(new CarHandling(355, -1065, 0.35), "cars/red_f1.png"));
		cars.add(new Car(new CarHandling(322, -966, 0.40), "cars/green_f1.png"));
		cars.add(new Car(new CarHandling(311, -933, 0.45), "cars/blue_f1.png"));
		
		// Serialize to json string
		String jsonString = gson.toJson(cars);		
		logger.info(jsonString);
		
		// Deserialize from json string
		ArrayList<Car> reCars = gson.fromJson(jsonString, new TypeToken<List<Car>>() {}.getType());
		
		for (Car c : reCars) {
			logger.info("{} {}", c.getCarImageLocation(), ((CarHandling) c.getCarHandling()).getAcceleration());
		}

	}

}
