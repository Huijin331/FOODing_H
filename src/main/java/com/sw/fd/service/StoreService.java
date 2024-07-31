package com.sw.fd.service;

import com.sw.fd.entity.Store;
import com.sw.fd.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class StoreService {
    @Autowired
    private LocationService locationService;

    @Autowired
    private StoreRepository storeRepository;

    @Transactional
    public void saveStore(Store store) {
        storeRepository.save(store);
    }

    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

    public Store getStoreById(int sno) {
        return storeRepository.findBySno(sno).orElse(null);
    }

    public List<Store> getStoresByCategory(String scate) {
        return storeRepository.findByScate(scate);
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
                nearbyStores.add(store);
            }
        }

        return nearbyStores;
    }
}
