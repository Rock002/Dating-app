package com.example.MeowDate.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Data
@Getter
@Setter
@Table(name = "photos")
@JsonIgnoreProperties({"user"})
public class Photo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "originFileName")
    private String originFileName;

    @Column(name = "isGeneralPhoto")
    private boolean isGeneralPhoto;

    @Column(name = "bytes")
    @Lob
    @Basic(fetch = FetchType.EAGER)
    private byte[] bytes;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    public Photo() {
    }

    public Photo(String originFileName, boolean isGeneralPhoto, byte[] bytes) {
        this.originFileName = originFileName;
        this.isGeneralPhoto = isGeneralPhoto;
        this.bytes = bytes;
    }
}
