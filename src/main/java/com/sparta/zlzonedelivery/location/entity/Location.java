package com.sparta.zlzonedelivery.location.entity;

import com.sparta.zlzonedelivery.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_lactions")
@SQLDelete(sql = "UPDATE p_lactions SET deleted_at = CURRENT_TIMESTAMP, is_public = false WHERE id = ?")
public class Location extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(length = 2, name = "ctrvn_cd")
    private String ctprvnCd;

    @Column(length = 40, name = "ctp_kor_nm")
    private String ctpKorNm;

    @Column(length = 5, name = "sig_cd")
    private String sigCd;

    @Column(length = 40, name = "sig_kor_nm")
    private String sigKorNm;

    @Column(length = 10, name = "emd_cd")
    private String emdCd;

    @Column(length = 40, name = "emd_kor_nm")
    private String emdKorNm;

    @Column(length = 10, name = "li_cd")
    private String liCd;

    @Column(length = 40, name = "li_kor_nm")
    private String liKorNm;






}
