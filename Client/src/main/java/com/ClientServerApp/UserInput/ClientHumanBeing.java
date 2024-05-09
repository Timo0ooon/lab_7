package com.ClientServerApp.UserInput;

import com.ClientServerApp.Model.Enums.Mood;
import com.ClientServerApp.Model.Enums.WeaponType;
import com.ClientServerApp.Model.HumanBeing.HumanBeing;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Scanner;
import java.util.function.Predicate;

public class ClientHumanBeing {
    private final Scanner scanner = new Scanner(System.in);
    private final HumanBeing humanBeing = new HumanBeing();

    public HumanBeing generate() {
        this.setID();
        this.setName(name -> !name.trim().replaceAll(" ", "").isEmpty());
        this.setCoordinates(el -> el > -507);
        this.setDate();
        this.setRealHero();
        this.setHasToothpick();
        this.setImpactSpeed(el -> el > -355);
        this.setWeaponType();
        this.setMood();
        this.setCar(name -> !name.trim().replaceAll(" ", "").isEmpty());

        return this.humanBeing;
    }

    private void setID() {}

    private void setName(Predicate<String> validator) {
        while (true) {

            System.out.print("Write name: ");
            String userName = scanner.nextLine();

            if (validator.test(userName)) {
                this.humanBeing.setName(userName);
                break;
            }
        }
    }

    private void setCoordinates(Predicate<Long> validator) {
        this.humanBeing.setCoordinates(new ClientCoordinates().generate(validator));
    }

    private void setDate() { this.humanBeing.setCreationDate(LocalDate.now()); }

    private void setRealHero() {
        while (true) {
            System.out.print("Is this person real? Write true or false: ");
            String user_line = scanner.nextLine();
            if (user_line.trim().equalsIgnoreCase("false") || user_line.trim().equalsIgnoreCase("f")) {
                this.humanBeing.setRealHero(false);
                break;
            }
            else if (user_line.trim().equalsIgnoreCase("true") || user_line.trim().equalsIgnoreCase("t")) {
                this.humanBeing.setRealHero(true);
                break;
            }
            else {
                System.out.print("Write values: true of false! ");
            }
        }
    }

    private void setHasToothpick() {
        while (true) {

            System.out.print("Does this person have a toothpick? Write true or false: ");

            String user_line = scanner.nextLine();
            if (user_line.trim().equalsIgnoreCase("false") || user_line.trim().equalsIgnoreCase("f")) {
                this.humanBeing.setHasToothpick(false);
                break;
            }
            else if (user_line.trim().equalsIgnoreCase("true") || user_line.trim().equalsIgnoreCase("t")) {
                this.humanBeing.setHasToothpick(true);
                break;
            }
            else {
                System.out.print("Write values: true of false! ");
            }
        }
    }

    private void setImpactSpeed(Predicate<Integer> validator) {

        while (true) {
            try {
                System.out.print("Write impactSpeed: ");
                Integer userLine = Integer.parseInt(scanner.nextLine());
                this.humanBeing.setImpactSpeed(userLine);
                if (validator.test(this.humanBeing.getImpactSpeed())) {
                    break;
                }
                System.out.print("Value must be greater than or equal to -355!");
            }

            catch(NumberFormatException e) {
                System.out.print("Value must be integer! ");
            }
        }
    }

    private void setWeaponType() {
        while (true) {
            StringBuilder sentence = new StringBuilder("Select the number from this list that you want to set:\n");
            WeaponType[] values = WeaponType.values();

            Arrays.stream(values).forEach(el -> sentence.append(Arrays.asList(values).indexOf(el) + 1).append(". ").append( el.name() ).append("\n") );

            System.out.println(sentence);
            System.out.print("Write: ");

            try {
                int user_value = scanner.nextInt();
                if ((1 <= user_value) && (user_value <= values.length)) {
                    this.humanBeing.setWeaponType(values[user_value - 1]);
                    break;
                }
                System.out.print("Value must be greater than 1 and less than " + values.length + ". ");
            }

            catch (NumberFormatException e) {
                System.out.print("Value must be integer! ");
            }
        }
    }

    private void setMood() {
        while (true) {
            StringBuilder sentence = new StringBuilder("Select the number from this list that you want to set:\n");
            Mood[] values = Mood.values();

            Arrays.stream(values).forEach(el -> {sentence.append(Arrays.asList(values).indexOf(el) + 1).append(". ").append( el.name() ).append("\n"); } );
            System.out.println(sentence);
            System.out.print("Write: ");

            try {
                int user_value = scanner.nextInt();
                if ((1 <= user_value) && (user_value <= values.length)) {
                    this.humanBeing.setMood(values[user_value - 1]);
                    break;
                }
                System.out.print("Value must be greater than 1 and less than " + values.length + ". ");
            }

            catch (NumberFormatException e) {
                System.out.print("Value must be integer! ");
            }
        }
    }

    private void setCar(Predicate<String> validator) {
        this.humanBeing.setCar(new ClientCar().generate(validator));
    }
}
