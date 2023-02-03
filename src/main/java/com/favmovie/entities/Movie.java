package com.favmovie.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "movie")
public class Movie {
    @Id
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String overview;
    private String poster_path;
    @Column(nullable = false)
    private LocalDate release_date;
    @Column(nullable = false)
    private Integer budget;
    @Column(nullable = false)
    private Integer revenue;
    @Column(nullable = false)
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Genre> genres;

    public void setRelease_date(String release_date) {
        this.release_date = LocalDate.parse(release_date);
    }
}
