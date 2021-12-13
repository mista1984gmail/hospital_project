package com.mista.soft.hospital_project.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="HistorySick")
@Table(name = "history_sick")
public class HistorySick {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 256)
    private String diagnosis;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private Type type;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name="date_of_action")
    private LocalDate dateOfAction;

    @OneToMany(mappedBy = "historySick", cascade = CascadeType.ALL)
    private List<AnalysisResult> analysisResults = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void setAnalysisResults(Integer id, String name, String value){
        this.analysisResults.add(new AnalysisResult(id, name, value, this));
    }

    public void addAnalysisResults(String name, String value){
        this.analysisResults.add(new AnalysisResult(name, value, this));
    }



}
