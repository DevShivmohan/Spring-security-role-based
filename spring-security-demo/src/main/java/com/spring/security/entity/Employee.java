package com.spring.security.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String profile;
    @ManyToOne
    @JoinColumn(name = "know_languages_id")
    private KnowLanguages knowLanguages;
    private long mobile;
    private Date captureDate;
}
