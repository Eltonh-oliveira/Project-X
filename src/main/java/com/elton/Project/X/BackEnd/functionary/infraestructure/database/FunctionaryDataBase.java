package com.elton.Project.X.BackEnd.functionary.infraestructure.database;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "functionary")
@Table(name = "functionary")
@EqualsAndHashCode(of = "id")
public class FunctionaryDataBase {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(unique = true)
    private String username;

    private String password;

    private String description;

    private boolean isActive;

    private String identifier;

    @CreationTimestamp
    private LocalDateTime modifiedAt;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "functionary")
    private List<FunctionaryContactDataBase> contacts;
}
