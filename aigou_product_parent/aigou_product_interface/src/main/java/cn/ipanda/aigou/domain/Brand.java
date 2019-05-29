package cn.ipanda.aigou.domain;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 品牌信息
 * </p>
 *
 * @author xwmtest
 * @since 2019-05-08
 */
@TableName("t_brand")
public class Brand extends Model<Brand> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    /**
     * 品牌id
     */
    private Long id;
    /**
     * 创建时间
     **/
    private Long createTime;
    /**
     * 修改时间
     **/
    private Long updateTime;
    /**
     * 用户姓名
     */
    private String name;
    /**
     * 英文名
     */
    private String englishName;
    /**
     * 首字母
     */
    private String firstLetter;
    /**
     * 产品描述比如四川特产！休闲食品！冰红茶雪碧奶粉！
     * */
    private String description;
    /**
     * 商品分类ID
     */
    @TableField("product_type_id")
    private Long productTypeId;
    /**
     * 商品品牌的商品类型！
     * SELECT FROM t_brand brand LEFT JOIN t_product_type product_type ON brand.product_type_id = product_type.id
     * list :单个对象：这个在数据库么有字段，要告诉mp
     *  C:\resource-maven\repository_final\com\baomidou\mybatis-plus-support\2.2.0\mybatis-plus-support-2.2.0-sources.jar!\com\baomidou\mybatisplus\annotations\TableField.java
     * <p>
     * 是否为数据库表字段
     * </p>
     * <p>
     * 默认 true 存在，false 不存在
     * </p>
     *  boolean exist() default true;
     */
    @TableField(exist =false)
    private ProductType productType;
    private Integer sortIndex;
    /**
     * 品牌LOGO
     */
    private String logo;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(Long productTypeId) {
        this.productTypeId = productTypeId;
    }

    public Integer getSortIndex() {
        return sortIndex;
    }

    public void setSortIndex(Integer sortIndex) {
        this.sortIndex = sortIndex;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Brand{" +
                ", id=" + id +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", name=" + name +
                ", englishName=" + englishName +
                ", firstLetter=" + firstLetter +
                ", description=" + description +
                ", productTypeId=" + productTypeId +
                ", sortIndex=" + sortIndex +
                ", logo=" + logo +
                "}";
    }
}
