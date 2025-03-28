package lv.visma.consulting.usercountriesapi.db.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @ManyToMany
    @JoinTable(
            name = "favorite_country", // This is the junction table
            joinColumns = @JoinColumn(name = "user_id"),  // Foreign key to 'user' table
            inverseJoinColumns = @JoinColumn(name = "country_id") // Foreign key to 'country' table
    )
    private Set<CountryEntity> favoriteCountries;
}
