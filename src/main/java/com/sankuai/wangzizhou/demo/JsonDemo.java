package com.sankuai.wangzizhou.demo;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.LocalDate;

class EvaluationDo {
    public LocalDate localDate;
    public String name;
    public Integer count;
    public Double ratio;
}
@JsonIgnoreProperties(value = {"localDate","name"})
class EvaluationDoV2 {
    LocalDate localDate;
    String name;
}
public class JsonDemo {
    public static void test() throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        //mapper.addMixIn(com.sankuai.wangzizhou.demo.EvaluationDo.class, com.sankuai.wangzizhou.demo.EvaluationDoV2.class);
        //mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        EvaluationDo evaluationDo = new EvaluationDo();
//        evaluationDo.setLocalDate(LocalDate.now());
//        evaluationDo.setName("binbarboo");
//        evaluationDo.setCount(100);
        String json = mapper.writeValueAsString(evaluationDo);
        System.out.println(json);
        EvaluationDo des = mapper.readValue(json, EvaluationDo.class);
        System.out.println(des);
    }
}
