package com.example.MeowDate.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
@Table(name = "photos")
public class Photo {
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
    private User user;

    public Photo() {
    }

    public Photo(String originFileName, boolean isGeneralPhoto, byte[] bytes) {
        this.originFileName = originFileName;
        this.isGeneralPhoto = isGeneralPhoto;
        this.bytes = bytes;
    }
}
