package model;

import dal.DBEntity;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Patrick Stapfer
 * Date: 25.03.12
 * Time: 18:59
 */
public class Angebot extends DBEntity {
    private Integer angebotID;
    private Double summe;
    private Integer dauer;
    private Date datum;
    private Double chance;

    public Angebot(){

    }

    public Integer getAngebotID() {
        return angebotID;
    }

    public void setAngebotID(Integer angebotID) {
        this.angebotID = angebotID;
    }

    public Double getSumme() {
        return summe;
    }

    public void setSumme(Double summe) {
        this.summe = summe;
    }

    public Integer getDauer() {
        return dauer;
    }

    public void setDauer(Integer dauer) {
        this.dauer = dauer;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public Double getChance() {
        return chance;
    }

    public void setChance(Double chance) {
        this.chance = chance;
    }

    public String toString(){
        return String.format("Angebot - ID: %s, Summe: %f, Dauer: %d, Datum: %s, Chance: %f", angebotID, summe, dauer, datum, chance);
    }
}
