package com.elton.Project.X.BackEnd.core.utils.contact.database;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Entity(name = "contact_type")
@Table(name = "contact_type")
@EqualsAndHashCode(of = "id")
public class ContactTypeDataBase {

    @Id
//    @GeneratedValue(generator = "UUID")
    private UUID id;

    private String label;

    @CreationTimestamp
    private LocalDateTime modifiedAt;

    @CreationTimestamp
    private LocalDateTime createdAt;


    public ContactTypeDataBase (String id, String label){
        this.id = UUID.fromString(id);
        this.label = label;
    }
}
