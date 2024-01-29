package com.elton.Project.X.BackEnd.functionary.infraestructure.database;

import com.elton.Project.X.BackEnd.core.utils.contact.database.ContactTypeDataBase;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "functionary_contact")
@Table(name = "functionary_contact")
@EqualsAndHashCode(of = "id")
public class FunctionaryContactDataBase {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private String label;

    @OneToOne
    private ContactTypeDataBase type;

    @CreationTimestamp
    private LocalDateTime modifiedAt;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public FunctionaryContactDataBase (String label, ContactTypeDataBase contactType) {
        this.label = label;
        this.type = contactType;
    }
}
