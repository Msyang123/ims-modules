package com.lhiot.ims.datacenter.feign.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lhiot.ims.datacenter.feign.entity.type.AttachmentType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


/**
 * @author xiaojian created in 2018/11/12 17:09
 **/
@Data
@ApiModel
public class Product {
    @ApiModelProperty(notes = "主键Id", dataType = "Long", readOnly = true)
    private Long id;
    @NotNull(message = "商品编码不能为空")
    @ApiModelProperty(notes = "商品编码", dataType = "String")
    private String code;
    @NotNull(message = "商品名称不能为空")
    private String name;
    @ApiModelProperty(notes = "分类ID", dataType = "Long")
    private Long categoryId;
    @ApiModelProperty(notes = "产地ID", dataType = "String")
    private String sourceCode;
    @ApiModelProperty(notes = "商品描述", dataType = "String")
    private String description;
    @ApiModelProperty(notes = "商品益处", dataType = "String")
    private String benefit;
    @ApiModelProperty(notes = "创建时间", dataType = "Date", readOnly = true)
    private Date createAt;
    @ApiModelProperty(notes = "商品附件信息", dataType = "ProductAttachment")
    private List<ProductAttachment> attachments;


    /**
     * 设置附件图片
     *
     * @param mainImg
     * @param subImgs
     * @param detailImgs
     * @param icon
     * @return
     */
    @JsonIgnore
    public List<ProductAttachment> setAttachmentImages(String mainImg, List<String> subImgs, List<String> detailImgs, String icon) {
        List<ProductAttachment> productAttachments = new ArrayList<>();
        if (Objects.nonNull(mainImg)) {
            ProductAttachment attachmentMainImg = new ProductAttachment();
            attachmentMainImg.setUrl(mainImg);
            attachmentMainImg.setSorting(1);
            attachmentMainImg.setAttachmentType(AttachmentType.MAIN_IMG);
            productAttachments.add(attachmentMainImg);
        }
        if (Objects.nonNull(subImgs)) {
            for (String imgs : subImgs) {
                ProductAttachment attachmentSubImg = new ProductAttachment();
                attachmentSubImg.setUrl(imgs);
                attachmentSubImg.setSorting(subImgs.indexOf(imgs) + 1);
                attachmentSubImg.setAttachmentType(AttachmentType.SUB_IMG);
                productAttachments.add(attachmentSubImg);
            }
        }
        if (Objects.nonNull(detailImgs)) {
            for (String imgs : detailImgs) {
                ProductAttachment attachmentDetailImg = new ProductAttachment();
                attachmentDetailImg.setUrl(imgs);
                attachmentDetailImg.setSorting(detailImgs.indexOf(imgs) + 1);
                attachmentDetailImg.setAttachmentType(AttachmentType.DETAIL_IMG);
                productAttachments.add(attachmentDetailImg);
            }
        }
        if (Objects.nonNull(icon)) {
            ProductAttachment attachmentIcon = new ProductAttachment();
            attachmentIcon.setUrl(icon);
            attachmentIcon.setSorting(1);
            attachmentIcon.setAttachmentType(AttachmentType.ICON);
            productAttachments.add(attachmentIcon);
        }
        return productAttachments;
    }
}
