package com.ClientServerApp.UserInput;

import com.ClientServerApp.Model.Car.Car;

import java.util.Scanner;
import java.util.function.Predicate;

/**
 *
 */
public class ClientCar {

    private final Car car = new Car();
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Generates a new object Car.
     * @param validator - checks machine name.
     * @return - Car object from Common packet.
     */
    public Car generate(Predicate<String> validator) {
        this.setName(validator);
        return this.car;
    }

    /**
     * Reads data from the console and writes to an object.
     * @param validator - checks machine name.
     */
    private void setName(Predicate<String> validator) {
        while (true) {
            System.out.print("Write car name: ");
            this.car.setName(scanner.nextLine());
            if (validator.test(this.car.getName())) {
                break;
            }
            System.out.print("Car name can not be empty! ");
        }
    }

    @Override
    public String toString() {
        return "ClientCar{" +
                "car=" + car +
                ", scanner=" + scanner +
                '}';
    }
}
