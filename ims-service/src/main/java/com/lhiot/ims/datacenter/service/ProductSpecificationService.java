package com.lhiot.ims.datacenter.service;

import com.leon.microx.web.result.Tips;
import com.lhiot.dc.dictionary.DictionaryClient;
import com.lhiot.dc.dictionary.module.Dictionary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author hufan created in 2018/11/23 9:42
 **/
@Service
@Transactional
public class ProductSpecificationService {

    private DictionaryClient dictionaryClient;

    public ProductSpecificationService(DictionaryClient dictionaryClient) {
        this.dictionaryClient = dictionaryClient;
    }

    /**
     * 获取所有基础规格单位列表
     *
     * @return
     */
    public Tips<List<String>> getUnits() {
        Optional<Dictionary> optional = dictionaryClient.dictionary("units");
        if (optional.isPresent()) {
            Dictionary dictionary = optional.get();
            List<String> units = dictionary.getEntries().stream().map(Dictionary.Entry::getName).collect(Collectors.toList());
            Tips<List<String>> tips = new Tips();
            tips.setData(units);
            return tips;
        }
        return null;
    }

}