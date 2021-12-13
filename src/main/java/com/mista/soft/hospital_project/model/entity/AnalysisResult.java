package com.mista.soft.hospital_project.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="AnalysisResult")
@Table(name = "analysis_result")
public class AnalysisResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @Column
    private String value;

    @ManyToOne
    @JoinColumn(name="history_sick_id")
    private HistorySick historySick;

    @Override
    public String toString() {
        return name + " : " + value;
    }

    public AnalysisResult(String name, String value, HistorySick historySick) {
        this.name = name;
        this.value = value;
        this.historySick = historySick;
    }
}
