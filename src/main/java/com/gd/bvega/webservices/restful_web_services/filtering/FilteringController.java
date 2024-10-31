package com.gd.bvega.webservices.restful_web_services.filtering;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FilteringController {

    @GetMapping("/filtering")
    public MappingJacksonValue filtering() {
        SomeBean someBean = new SomeBean("value1", "value2", "value3");

        return setFilters(someBean, new String[]{"field1", "field2"});
    }

    @GetMapping("/filtering-list")
    public MappingJacksonValue filteringList() {
        List<SomeBean> someBeans = List.of(new SomeBean("value1", "value2", "value3")
        , new SomeBean("value4", "value5", "value6"));

        return setFilters(someBeans, "field1", "field3");
    }

    private <T> MappingJacksonValue setFilters(T bean, String... filterFields) {
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(bean);

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(filterFields);

        FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
        mappingJacksonValue.setFilters(filters);

        return mappingJacksonValue;
    }

}
