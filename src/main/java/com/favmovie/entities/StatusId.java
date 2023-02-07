package com.favmovie.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Embeddable
public class StatusId implements Serializable {
    @Serial
    private static final long serialVersionUID = -3112953729008570664L;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "movie_id")
    private Long movieId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        StatusId statusId = (StatusId) o;
        return Objects.equals(userId, statusId.userId) &&
                Objects.equals(movieId, statusId.movieId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, movieId);
    }
}
