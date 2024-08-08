package com.sw.fd.service;

import com.sw.fd.entity.ReviewTag;
import com.sw.fd.entity.Store;
import com.sw.fd.entity.StoreTag;
import com.sw.fd.entity.Tag;
import com.sw.fd.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StoreService {
    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private ReviewTagRepository reviewTagRepository;

    @Autowired
    private StoreTagRepository storeTagRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private PickRepository pickRepository;

    @Autowired
    private LocationService locationService;

    @Autowired
    private TagRepository tagRepository;

    public void saveStore(Store store) {
        storeRepository.save(store);
        updateStoreTags(store);
    }

    public List<StoreTag> getStoreTagsByStoreSno(int sno) {
        return storeTagRepository.findByStore_Sno(sno);
    }

    @Transactional
    public void updateStoreTags(Store store) {
        List<ReviewTag> reviewTags = reviewTagRepository.findByReview_Store_Sno(store.getSno());

        if (reviewTags.isEmpty()) {
            return;
        }

        Map<Integer, Long> tagCountMap = new HashMap<>();
        for (ReviewTag reviewTag : reviewTags) {
            int tno = reviewTag.getTag().getTno();
            tagCountMap.put(tno, tagCountMap.getOrDefault(tno, 0L) + 1);
        }

        for (Map.Entry<Integer, Long> entry : tagCountMap.entrySet()) {
            int tno = entry.getKey();
            long count = entry.getValue();

            List<StoreTag> storeTags = storeTagRepository.findByStore_SnoAndTag_Tno(store.getSno(), tno);
            StoreTag storeTag;

            if (storeTags.isEmpty()) {
                storeTag = new StoreTag();
                storeTag.setStore(store);
                Tag tag = tagRepository.findByTno(tno);
                storeTag.setTag(tag);
                storeTag.setTagCount((int) count);
            } else {
                storeTag = storeTags.get(0);
                /*System.out.println("값 갱신전: " + storeTag.getTag().getTtag() + "의 수: " + storeTag.getTagCount());*/
                storeTag.setTagCount((int) count);
                /*System.out.println("값 갱신후: " + storeTag.getTag().getTtag() + "의 수: " + storeTag.getTagCount());*/
            }

            storeTagRepository.save(storeTag);
        }

        /*System.out.println("updateStoreTags 동작 완료");*/
    }

    public List<Store> getAllStores() {
        List<Store> stores = storeRepository.findAll();
        for (Store store : stores) {
            updateStoreTags(store);
        }
        return stores;
    }

    public Store getStoreAllInfo(int sno) {
        Store store = storeRepository.findBySno(sno).orElse(null);
        if (store != null) {
            updateStoreTags(store);

            // 별점 평균 계산
            Double averageScore = reviewRepository.findAverageScoreBySno(sno);
            store.setScoreArg(averageScore != null ? averageScore : 0);

            // Pick 수 계산
            int pickCount = pickRepository.countBySno(sno);
            store.setPickNum(pickCount);
        }

        return store;
    }

    public Store getStoreById(int sno) {
        Store store = storeRepository.findBySno(sno).orElse(null);
        if (store != null) {
            updateStoreTags(store);
        }
        return store;
    }

    public List<Store> getStoresByCategory(String scate) {
        List<Store> stores = storeRepository.findByScate(scate);
        for (Store store : stores) {
            updateStoreTags(store);
        }
        return stores;
    }

    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radius of the earth in km
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; // Distance in km
    }

    public List<Store> getNearbyStores(double userLat, double userLon) {
        List<Store> allStores = storeRepository.findAll();
        List<Store> nearbyStores = new ArrayList<>();

        for (Store store : allStores) {
            double[] coordinates = locationService.getCoordinates(store.getSaddr());
            double distance = calculateDistance(userLat, userLon, coordinates[0], coordinates[1]);
            if (distance <= 2) {
                store.setDistance(distance); // 거리 저장하는 부분
                nearbyStores.add(store);
            }
        }
        return nearbyStores;
    }

}
