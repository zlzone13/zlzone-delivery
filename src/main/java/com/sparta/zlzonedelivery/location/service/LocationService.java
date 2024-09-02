package com.sparta.zlzonedelivery.location.service;

import com.sparta.zlzonedelivery.location.entity.Location;
import com.sparta.zlzonedelivery.location.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    @Transactional
    public Location findByLocationName(Location location) {

        return locationRepository.findByCtpKorNmAndSigKorNmAndEmdKorNmAndLiKorNm(location.getCtpKorNm(),
                        location.getSigKorNm(), location.getEmdKorNm(), location.getLiKorNm())
                .orElseGet(()->locationRepository.save(location));
    }

}
