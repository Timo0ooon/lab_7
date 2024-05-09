package com.ClientServerApp.Model.HumanBeing;

import com.ClientServerApp.Model.Car.Car;
import com.ClientServerApp.Model.Coordinates.Coordinates;
import com.ClientServerApp.Model.Enums.Mood;
import com.ClientServerApp.Model.Enums.WeaponType;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class HumanBeing implements Serializable {
    @Serial
    private static final long serialVersionUID = 1011L;
    protected Integer id;  // Field can not be null. Value must be greater than 0. Value must be unique, The value should be generated automatically.
    protected String name;  // Field can not be null. Value can not be empty line.
    protected Coordinates coordinates;  // Field can not be null.
    protected LocalDate creationDate;  // Field can not be null. The value should be generated automatically.
    protected Boolean realHero;  // Field can not be null.
    protected Boolean hasToothpick;  // Field can not be null.
    protected Integer impactSpeed;  // Value must be greater than -355. Field can not be null.
    protected WeaponType weaponType;  // Field can not be null.
    protected Mood mood;  // Field can not be null.
    protected Car car;  // Field can not be null.

    public HumanBeing() {}

    public HumanBeing (
            Integer id, String name, Coordinates coordinates, LocalDate creationDate, Boolean realHero, Boolean hasToothpick,
            Integer impactSpeed, WeaponType weaponType, Mood mood, Car car
    ) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate= creationDate;
        this.realHero = realHero;
        this.hasToothpick = hasToothpick;
        this.impactSpeed = impactSpeed;
        this.weaponType = weaponType;
        this.mood = mood;
        this.car = car;
    }

    public Integer getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Boolean getRealHero() {
        return realHero;
    }

    public Boolean getHasToothpick() {
        return hasToothpick;
    }

    public Integer getImpactSpeed() {
        return impactSpeed;
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }

    public Mood getMood() {
        return mood;
    }

    public Car getCar() {
        return car;
    }

    public void setID(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public void setRealHero(Boolean realHero) {
        this.realHero = realHero;
    }

    public void setHasToothpick(Boolean hasToothpick) {
        this.hasToothpick = hasToothpick;
    }

    public void setImpactSpeed(Integer impactSpeed) {
        this.impactSpeed = impactSpeed;
    }

    public void setWeaponType(WeaponType weaponType) {
        this.weaponType = weaponType;
    }

    public void setMood(Mood mood) {
        this.mood = mood;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        HumanBeing that = (HumanBeing) object;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(coordinates, that.coordinates) && Objects.equals(creationDate, that.creationDate) && Objects.equals(realHero, that.realHero) && Objects.equals(hasToothpick, that.hasToothpick) && Objects.equals(impactSpeed, that.impactSpeed) && Objects.equals(weaponType, that.weaponType) && Objects.equals(mood, that.mood) && Objects.equals(car, that.car);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, realHero, hasToothpick, impactSpeed, weaponType, mood, car);
    }

    @Override
    public String toString() {
        return "HumanBeing{" +
                "id=" + id +
                ", name=" + name  +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", realHero=" + realHero +
                ", hasToothpick=" + hasToothpick +
                ", impactSpeed=" + impactSpeed +
                ", weaponType=" + weaponType +
                ", mood=" + mood +
                ", car=" + car +
                '}';
    }
}
