package com.mista.soft.hospital_project.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="Type")
@Table(name = "type")
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 45, nullable = false, unique = true)
    private String name;
    @Column
    private double price;

    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;

    @Override
    public String toString() {
        return name;
    }
}
