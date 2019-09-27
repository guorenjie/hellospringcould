package com.grj.eurekabook.entity;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author guorenjie
 * @since 2019-09-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Book对象", description="")
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    private String id;

    @ApiModelProperty(value = "书籍编号")
    private String bookNum;

    @ApiModelProperty(value = "数据名称")
    private String bookName;

    @ApiModelProperty(value = "作者")
    private String bookAuthor;

    @ApiModelProperty(value = "用户id")
    private String userId;


}
