    package com.bookinghive.project.service.impl;

    import com.bookinghive.project.entity.City;
    import com.bookinghive.project.model.CityModel;
    import com.bookinghive.project.repository.CityRepository;
    import com.bookinghive.project.service.CityService;
    import jakarta.transaction.Transactional;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;
    import java.util.ArrayList;
    import java.util.List;

    @Service
    public class CityServiceImpl implements CityService {

        @Autowired
        private CityRepository cityRepository;

        @Transactional
        @Override
        public CityModel saveCity(CityModel cityModel) {
            City city = new City();
            city.setCityName(cityModel.getCityName());
            city = cityRepository.save(city);
            cityModel.setId(city.getId());

            return cityModel;
        }

        @Override
        public List<CityModel> getAllCities() {
            List<CityModel> cityModels = new ArrayList<>();
            List<City> cities = cityRepository.findAll();

            for (City city : cities) {
                CityModel cityModel = new CityModel();
                cityModel.setId(city.getId());
                cityModel.setCityName(city.getCityName());
                cityModels.add(cityModel);
            }

            return cityModels;
        }

        @Override
        public City getCityById(Long cityId) {
            return cityRepository.findById(cityId)
                    .orElseThrow(() -> new RuntimeException("City not found with ID: " + cityId));
        }
    }
