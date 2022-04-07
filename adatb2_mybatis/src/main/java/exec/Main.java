package exec;

import database_functions.CarDAO;
import database_functions.MyBatisUtil;
import model.Car;
import oracle.jdbc.aq.AQDequeueOptions;

import java.io.IOException;

public class Main {
    private static final CarDAO carDAO = new CarDAO();

    public static void main(String[] args) {
        try {
            new MyBatisUtil();
            System.out.println("connected...");

            insertCar(new Car(30,"Suzuki","zöld",200,1));
            deleteCar(30);
            updateCar(new Car(12,"Suzuki","sárga",100));
            selectAllCar();

            System.out.println("Success");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static void insertCar(Car car) {
        carDAO.insert(car);
    }

    public static void selectAllCar() {
        for (Car car : carDAO.selectAll()) {
            System.out.println(car);
        }
    }

    public static void deleteCar(int id) {
        carDAO.deleteById(id);
    }

    public static void updateCar(Car car) {
        carDAO.updateById(car);
    }

}
