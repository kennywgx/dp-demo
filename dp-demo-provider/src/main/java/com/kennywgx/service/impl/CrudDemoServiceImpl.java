package com.kennywgx.service.impl;

import com.kennywgx.domain.DemoDo;
import com.kennywgx.mapper.DemoMapper;
import com.kennywgx.service.CrudDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrudDemoServiceImpl implements CrudDemoService {
    @Autowired
    private DemoMapper mapper;

    @Override
    public List<DemoDo> listAll() {
        return mapper.selectAll();
    }
}
