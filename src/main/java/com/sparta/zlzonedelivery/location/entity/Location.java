package com.sparta.zlzonedelivery.location.entity;

import com.sparta.zlzonedelivery.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_locations", uniqueConstraints = {@UniqueConstraint(name = "uniqueLocation",
        columnNames = {"ctpKorNm", "sigKorNm", "emdKorNm", "liKorNm"})})
@SQLDelete(sql = "UPDATE p_locations SET deleted_at = CURRENT_TIMESTAMP, is_public = false WHERE id = ?")
@SQLRestriction("is_public=true")
public class Location extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 2, name = "ctrvn_cd")
    private String ctprvnCd;

    @Column(length = 40, name = "ctp_kor_nm")
    @Getter
    private String ctpKorNm;

    @Column(length = 5, name = "sig_cd")
    @Getter
    private String sigCd;

    @Column(length = 40, name = "sig_kor_nm")
    @Getter
    private String sigKorNm;

    @Column(length = 10, name = "emd_cd")
    private String emdCd;

    @Column(length = 40, name = "emd_kor_nm")
    @Getter
    private String emdKorNm;

    @Column(length = 10, name = "li_cd")
    private String liCd;

    @Column(length = 40, name = "li_kor_nm")
    @Getter
    private String liKorNm;

    @Builder
    public Location (String ctprvnCd, String ctpKorNm,
                     String sigCd, String sigKorNm,
                     String emdCd, String emdKorNm,
                     String liCd, String liKorNm) {
        this.ctprvnCd = ctprvnCd;
        this.ctpKorNm = ctpKorNm;
        this.sigCd = sigCd;
        this.sigKorNm = sigKorNm;
        this.emdCd = emdCd;
        this.emdKorNm = emdKorNm;
        this.liCd = liCd;
        this.liKorNm = liKorNm;
    }







}
