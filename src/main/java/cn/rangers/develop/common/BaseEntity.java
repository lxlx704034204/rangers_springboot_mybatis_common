package cn.rangers.develop.common;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * 基类实体
 * 
 * @version 1.0
 * @since JDK1.7
 * @author Administrator
 * @company 令其小屋
 * @copyright (c) 2016 SunTime Co'Ltd Inc. All rights reserved.
 * @date 2016年7月31日 下午3:44:03
 */
public abstract class BaseEntity implements Serializable {

	/**
	 * 存放一些通用的属性值
	 */
	private static final long serialVersionUID = 1L;
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
//	/**@JsonIgnore 忽略序列化*/
//	@JsonIgnore
//	public static final Integer SHOW = 1;
//	@JsonIgnore
//	public static final Integer HIDE = 0;
//	
//    private Date createDate;
//    
//    private UserInfo createBy;
//    
//    private Date updateDate;
//    
//    private UserInfo updateBy;
//
//	public Date getCreateDate() {
//		return createDate;
//	}
//
//	public void setCreateDate(Date createDate) {
//		this.createDate = createDate;
//	}
//
//	public UserInfo getCreateBy() {
//		return createBy;
//	}
//
//	public void setCreateBy(UserInfo createBy) {
//		this.createBy = createBy;
//	}
//
//	public Date getUpdateDate() {
//		return updateDate;
//	}
//
//	public void setUpdateDate(Date updateDate) {
//		this.updateDate = updateDate;
//	}
//
//	public UserInfo getUpdateBy() {
//		return updateBy;
//	}
//
//	public void setUpdateBy(UserInfo updateBy) {
//		this.updateBy = updateBy;
//	}
    
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Transient
    private Integer page = 1;

    @Transient
    private Integer rows = 10;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }
}