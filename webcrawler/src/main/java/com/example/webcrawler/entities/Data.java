package com.example.webcrawler.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="search_data")
public class Data {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "makes")
    private String makes;

    @Column(name = "searchDate")
    private Date date;
}
