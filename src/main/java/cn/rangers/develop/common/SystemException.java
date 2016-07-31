package cn.rangers.develop.common;

/**
 * 自定义系统异常
 * 
 * @version 1.0
 * @since JDK1.7
 * @author Administrator
 * @company 令其小屋
 * @copyright (c) 2016 SunTime Co'Ltd Inc. All rights reserved.
 * @date 2016年7月31日 下午3:46:31
 */
public class SystemException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String code;
	public SystemException(String msg, String code) {
		super(msg);
		this.code = code;
	}
	public SystemException(String msg) {
	    super(msg);
	}
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public static long getSerialversionuid() {
        return serialVersionUID;
    }
}
