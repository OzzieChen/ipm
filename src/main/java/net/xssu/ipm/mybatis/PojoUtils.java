package net.xssu.ipm.mybatis;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.xssu.ipm.annotation.Column;
import net.xssu.ipm.annotation.Pk;
import net.xssu.ipm.annotation.Table;
import net.xssu.ipm.exception.PojoException;

/**
 * @description
 * @version v1.0
 */
public class PojoUtils {

	static class Infos {

		List<ColInfo> mappings;
		List<ColInfo> pks;
		String table;

		public Infos(List<ColInfo> mappings, List<ColInfo> pks, String table) {
			this.mappings = mappings;
			this.pks = pks;
			this.table = table;
		}
	}

	static class ColInfo {

		String column;
		String prop;
		boolean eq;
		boolean isPk;
		String op = "=";

		public ColInfo(String column, String prop) {
			this.column = column;
			this.prop = prop;
			this.isPk = false;
			this.eq = true;
		}

		public ColInfo(String column, String prop, boolean isPk) {
			this.column = column;
			this.prop = prop;
			this.isPk = isPk;
			this.eq = true;
		}

	}

	private static Map<Class<?>,Infos> columnMap = new HashMap<Class<?>,Infos>();

	public static String from(Object obj) {
		return columnMap.get(obj.getClass()).table;
	}

	public static List<ColInfo> primaryKeys(Object obj) {
		return columnMap.get(obj.getClass()).pks;
	}

	public static void caculationColumnList(Object obj) {
		if(columnMap.containsKey(obj.getClass()))
			return;
		String tablename;
		List<ColInfo> pks = new ArrayList<ColInfo>();
		Table table = obj.getClass().getAnnotation(Table.class);
		if(table != null)
			tablename = table.name();
		else
			throw new PojoException("POJO Annotation \"@Table(tableName)\" undefined ");

		Field[] fields = obj.getClass().getDeclaredFields();
		ArrayList<ColInfo> columnList = new ArrayList<ColInfo>(fields.length);

		for(Field field : fields){

			Pk pk = field.getAnnotation(Pk.class);
			ColInfo c = null;
			if(pk != null){
				if("".equals(pk.name())){
					c = new ColInfo(field.getName(), field.getName(), true);
				}else{
					c = new ColInfo(pk.name(), field.getName(), true);
					c.eq = false;
				}
				pks.add(c);
			}else{
				Column col = field.getAnnotation(Column.class);
				if(col != null){
					if("".equals(col.name())){
						c = new ColInfo(field.getName(), field.getName());
					}else{
						c = new ColInfo(col.name(), field.getName());
						c.eq = false;
					}
				}
			}
			if(c != null)
				columnList.add(c);
		}
		columnMap.put(obj.getClass(), new Infos(columnList, pks, tablename));
	}

	public static String foreach(List<ColInfo> map, String separator, boolean col, boolean prop) {
		StringBuilder sb = new StringBuilder();
		if(col && !prop){
			for(ColInfo entry : map){
				sb.append(entry.column);
				sb.append(separator);
			}
			sb.deleteCharAt(sb.length() - 1);
		}else if(!col && prop){
			for(ColInfo entry : map){
				sb.append("#{");
				sb.append(entry.prop);
				sb.append("}");
				sb.append(separator);
			}
			sb.deleteCharAt(sb.length() - 1);
		}else{
			for(ColInfo entry : map){
				sb.append(entry.column);
				sb.append("=#{");
				sb.append(entry.prop);
				sb.append("}");
				if(map.size() > 1)
					sb.append(separator);
			}
			if(map.size() > 1)
				sb.delete(sb.length() - separator.length(), sb.length());
		}
		return sb.toString();
	}

	public static String foreach(List<ColInfo> map, String separator, boolean col, boolean prop, String pre) {
		StringBuilder sb = new StringBuilder();
		if(col && !prop){
			for(ColInfo entry : map){
				sb.append(entry.column);
				sb.append(separator);
			}
			sb.deleteCharAt(sb.length() - 1);
		}else if(!col && prop){
			for(ColInfo entry : map){
				sb.append("#{");
				sb.append(entry.prop);
				sb.append("}");
				sb.append(separator);
			}
			sb.deleteCharAt(sb.length() - 1);
		}else{
			for(ColInfo entry : map){
				sb.append(entry.column);
				sb.append("=#{").append(pre);
				sb.append(entry.prop);
				sb.append("}");
				if(map.size() > 1)
					sb.append(separator);
			}
			if(map.size() > 1)
				sb.delete(sb.length() - separator.length(), sb.length());
		}
		return sb.toString();
	}

	private static boolean isNull(String fieldname, Object obj) {
		try{
			Field field = obj.getClass().getDeclaredField(fieldname);
			field.setAccessible(true);
			Object objj = field.get(obj);
			return objj == null;
		}catch(SecurityException e){
			e.printStackTrace();
		}catch(NoSuchFieldException e){
			e.printStackTrace();
		}catch(IllegalArgumentException e){
			e.printStackTrace();
		}catch(IllegalAccessException e){
			e.printStackTrace();
		}
		return false;
	}

	public static List<ColInfo> notNullColumns(Object obj) {
		List<ColInfo> list = columnMap.get(obj.getClass()).mappings;
		List<ColInfo> nl = new ArrayList<ColInfo>();
		for(ColInfo column : list){
			if(isNull(column.prop, obj))
				continue;
			nl.add(column);
		}
		return nl;
	}

	public static List<ColInfo> updateColumns(Object obj) {
		List<ColInfo> list = columnMap.get(obj.getClass()).mappings;
		List<ColInfo> nl = new ArrayList<ColInfo>();
		for(ColInfo column : list){
			if(isNull(column.prop, obj) || column.isPk)
				continue;
			nl.add(column);
		}
		return nl;
	}

	public static String where(List<ColInfo> map, String separator) {
		StringBuilder sb = new StringBuilder();
		int x = 0;
		for(ColInfo entry : map){
			if(x > 0)
				sb.append(separator);
			sb.append(entry.column);
			if(entry.op.length() < 4){
				sb.append(entry.op);
				sb.append("#{");
				sb.append(entry.prop);
				sb.append("}");
			}else{
				sb.append(" LIKE CONCAT(CONCAT('%',#{");
				sb.append(entry.prop);
				sb.append("}),'%')");
			}
			x++;
		}
		//sb.delete(sb.length() - separator.length(), sb.length());

		return sb.toString();
	}

	public static String selectColumns(Object obj) {
		StringBuilder sb = new StringBuilder();

		List<ColInfo> list = columnMap.get(obj.getClass()).mappings;
		for(ColInfo column : list){
			sb.append(column.column);
			if(!column.eq){
				sb.append(" ");
				sb.append(column.prop);
			}
			sb.append(",");
		}
		if(sb.length() > 1)
			sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}
}
