package com.example.MeowDate.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
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

    public String getOriginFileName() {
        return originFileName;
    }

    public Long getId() {
        return id;
    }

    public boolean isGeneralPhoto() {
        return isGeneralPhoto;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setOriginFileName(String originFileName) {
        this.originFileName = originFileName;
    }

    public void setGeneralPhoto(boolean generalPhoto) {
        isGeneralPhoto = generalPhoto;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
