package com.example.demo.mapper;


import com.example.demo.Bean.Bin;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface userInfMapper {

    Bin getOneBin(Integer id);

    void changeLevel(int id, double level);
}
