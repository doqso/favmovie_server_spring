package com.favmovie.entities;

import com.favmovie.util.ExternalApiUtil;
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
    @Column(nullable = false, length = 1000)
    private String overview;
    @Column(nullable = true)
    private String poster_path;
    @Column(nullable = false)
    private LocalDate release_date;
    @Column(nullable = false)
    private Long budget;
    @Column(nullable = false)
    private Long revenue;
    @Column(nullable = false)
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Genre> genres;
    public void setRelease_date(String release_date) {
        this.release_date = LocalDate.parse(release_date);
    }

    public void setPoster_path(String poster_path) {
        if (poster_path != null && poster_path.startsWith("/"))
            this.poster_path = ExternalApiUtil.IMAGE_BASE_URL.concat(poster_path);
        else this.poster_path = poster_path;
    }
}
