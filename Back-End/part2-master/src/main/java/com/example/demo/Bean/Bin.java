package com.example.demo.Bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("T_binList")
public class Bin {
    @TableId(value = "PK_BID", type = IdType.AUTO)
    private Integer id;

    @TableField("Blatitude")
    private String latitude;

    @TableField("Blongitude")
    private String longitude;

    @TableField("Blevel")
    private double level;

    @TableField("Bstate")
    private int state;

    @TableField("BotherThing")
    private String otherThing;
}
