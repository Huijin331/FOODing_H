package com.sw.fd.controller;

import com.sw.fd.entity.Menu;
import com.sw.fd.entity.Store;
import com.sw.fd.service.MenuService;
import com.sw.fd.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class StoreController {

    @Autowired
    private StoreService storeService;
    @Autowired
    private MenuService menuService;

    private static final double DEFAULT_LAT = 35.8799906; // 대구광역시 동구 동부로 121의 위도
    private static final double DEFAULT_LON = 128.6286206; // 대구광역시 동구 동부로 121의 경도

    @GetMapping("/storeList")
    public String showStoreList(@RequestParam(value = "scate", required = false) String scate, Model model) {
        List<Store> stores;
        if (scate != null && !scate.isEmpty()) {
            stores = storeService.getStoresByCategory(scate);
        } else {
            stores = storeService.getAllStores();
        }
        model.addAttribute("stores", stores);
        return "storeList";
    }

    @GetMapping("/storeDetail")
    public String storeDetail(@RequestParam("sno") int sno, Model model) {
        Store store = storeService.getStoreById(sno);
        List<Menu> menus = menuService.getMenuBySno(sno);
        model.addAttribute("store", store);
        model.addAttribute("menus", menus);
        return "storeDetail";
    }
    @GetMapping("/storeInfo")
    public String storeInfo(@RequestParam("sno") int sno, Model model) {
        Store store = storeService.getStoreById(sno);
        List<Menu> menus = menuService.getMenuBySno(sno);
        model.addAttribute("store", store);
        model.addAttribute("menus", menus);
        return "storeInfo"; // storeInfo.jsp로 포워드
    }

    @GetMapping("/storeListByLocation")
    public String showStoreListByLocation(
            @RequestParam(value = "userLat", required = false) Double userLat,
            @RequestParam(value = "userLon", required = false) Double userLon,
            Model model) {

        if (userLat == null || userLon == null) {
            userLat = DEFAULT_LAT;
            userLon = DEFAULT_LON;
        }

        List<Store> stores = storeService.getNearbyStores(userLat, userLon);
        model.addAttribute("stores", stores);
        model.addAttribute("defaultLat", DEFAULT_LAT);
        model.addAttribute("defaultLon", DEFAULT_LON);
        return "storeListByLocation";
    }


}
