package com.sparta.zlzonedelivery.store.repository;

import com.sparta.zlzonedelivery.store.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface StoreRepository extends JpaRepository<Store, UUID> {

    Optional<Store> findByIdAndIsPublicIsTrue(UUID uuid);

    Page<Store> findAllByIsPublicIsTrue(Pageable pageable);

    boolean existsByIdAndIsPublicIsTrue(UUID uuid);

    Page<Store> findByLocation_CtpKorNmContainingAndIsPublicIsTrue(Pageable pageable, String ctpKorNm);


    @Query("""
        select s
        from Store s
        join fetch s.location l
        where s.location.ctpKorNm like concat('%', :ctp_kor_nm, '%')
          and s.location.sigKorNm like concat('%', :sig_kor_nm, '%')
          and s.isPublic = true
    """)
    Page<Store> findByLocation_CtpKorNmAndLocation_SigKorNm(Pageable pageable, @Param("ctp_kor_nm") String ctpKorNm,
                                                                      @Param("sig_kor_nm") String sigKorNm);

    @Query("""
        select s
        from Store s
        join fetch s.location l
        where s.location.ctpKorNm like concat('%', :ctp_kor_nm, '%')
          and s.location.sigKorNm like concat('%', :sig_kor_nm, '%')
          and ( (s.location.emdKorNm like concat('%', :emd_kor_nm, '%') or :emd_kor_nm is null)
             or (s.location.liKorNm like concat('%', :li_kor_nm, '%') or :li_kor_nm is null) )
          and s.isPublic = true
    """)
    Page<Store> findByAllLocation (Pageable pageable, @Param("ctp_kor_nm") String ctpKorNm,
                                   @Param("sig_kor_nm") String sigKorNm, @Param("emd_kor_nm") String emdKorNm,
                                   @Param("li_kor_nm")String liKorNm);

}
