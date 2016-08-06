package cn.rangers.develop.utils;

/**
 * 
 * 从excel中获取数据的时候，对列添加的条件
 * @version 1.0
 * @since JDK1.7
 * @author fuhw
 * @date 2016年4月6日 下午4:38:42
 */
public enum ExcelReadConditions {
       columns;
       public static String SUB_STRING = "sub_string";//截取掉字段中的后缀
       public static String FILTER_STRING = "filter_string";//过滤掉不存储的记录
       public static String FORMAT_STRING = "format_string";//格式化操作，不足6位补零
}
