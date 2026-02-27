package com.teamkeon.bigpawsbackend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id", nullable = false)
    @JsonIgnore
    private Place place;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int kindnessScore;      // 친절도 1~5

    @Column(nullable = false)
    private int sizeScore;          // 매장 크기 1~5

    @Column(nullable = false)
    private int bigDogScore;        // 대형견 수용도 1~5
}
