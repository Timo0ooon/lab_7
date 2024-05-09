package com.ClientServerApp.UserInput;

import com.ClientServerApp.Model.Coordinates.Coordinates;

import java.util.Scanner;
import java.util.function.Predicate;

public class ClientCoordinates {
    private final Coordinates coordinates = new Coordinates();
    private final Scanner scanner = new Scanner(System.in);

    public Coordinates generate(Predicate<Long> validatorY) {
        this.set_X_coordinate_value();
        this.set_Y_coordinate_value(validatorY);
        return this.coordinates;
    }

    private void set_X_coordinate_value() {
        while (true) {
            try {
                System.out.print("Write coordinate X: ");
                this.coordinates.setX(scanner.nextDouble());
                break;
            }
            catch (NumberFormatException e) {
                System.out.print("Write a number! ");

            }
        }
    }

    private void set_Y_coordinate_value(Predicate<Long> validator) {

        while (true) {
            try {
                System.out.print("Write coordinate Y: ");
                this.coordinates.setY(scanner.nextLong());

                if (validator.test(this.coordinates.getY()))
                    break;

                System.out.print("Write a value greater than -507! ");

            }

            catch (NumberFormatException e) {
                System.out.print("Please write a number! ");
            }
        }
    }

    @Override
    public String toString() {
        return "X coordinate: " + this.coordinates.getX() + "\n" + "Y coordinate: " + this.coordinates.getY();
    }
}
