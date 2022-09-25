package com.example.webcrawler.entities;

import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "makes")
    private String makes;

    @Column(name = "model")
    private String model;

    @Column(name = "fuelType")
    private String fuelType;

    @Column(name = "age")
    private int age;

    @Column(name = "engine")
    private int engine;

    @Column(name = "hp")
    private int hp;

    @Column(name = "mileage")
    private  String mileage;

    @Column(name = "price")
    private double price;

    @Column(name = "image")
    private String image;
    @Column(name = "link")
    private String link;
}
