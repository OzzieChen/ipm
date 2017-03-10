package net.xssu.ipm.mybatis;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.DELETE_FROM;
import static org.apache.ibatis.jdbc.SqlBuilder.FROM;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.SELECT;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;

import java.util.List;
import java.util.Map;

import net.xssu.ipm.mybatis.PojoUtils.ColInfo;

/**
 * @description TODO
 * @version v1.0
 */
public class CrudTemplet {

	//		public String insertBatching(Object obj) {
	//			@SuppressWarnings("rawtypes")
	//			Map map = (Map)obj;
	//			Object o = map.get("1");
	//			PojoUtils.caculationColumnList(o);
	//			List<ColInfo> cols = PojoUtils.notNullColumns(o);
	//			StringBuilder sb = new StringBuilder();
	//			sb.append("INSERT INTO ");
	//			sb.append(PojoUtils.from(o));
	//			sb.append(" (");
	//			sb.append(PojoUtils.foreach(cols, ",", true, false));
	//			
	//	
	//			VALUES(, PojoUtils.foreach(cols, ",", false, true));
	//			for(ColInfo column : cols){
	//				sb.append("(");
	//				sb.append(column.column);
	//				if(!column.eq){
	//					sb.append(" ");
	//					sb.append(column.prop);
	//				}
	//				sb.append(",");
	//			}
	//			sb.deleteCharAt(sb.length() - 1);
	//			return SQL();
	//		}

	public String insert(Object obj) {
		PojoUtils.caculationColumnList(obj);
		List<ColInfo> cols = PojoUtils.notNullColumns(obj);
		BEGIN();

		INSERT_INTO(PojoUtils.from(obj));

		VALUES(PojoUtils.foreach(cols, ",", true, false), PojoUtils.foreach(cols, ",", false, true));
		return SQL();
	}

	public String update(Object obj) {
		PojoUtils.caculationColumnList(obj);
		List<ColInfo> pks = PojoUtils.primaryKeys(obj);
		BEGIN();

		UPDATE(PojoUtils.from(obj));

		SET(PojoUtils.foreach(PojoUtils.updateColumns(obj), ",", true, true));

		WHERE(PojoUtils.foreach(pks, " AND ", true, true));
		return SQL();
	}

	public String updateByWhere(Object map) {
		@SuppressWarnings("unchecked")
		Map<Object,Object> m = (Map<Object,Object>) map;
		Object o = m.get("o");
		PojoUtils.caculationColumnList(o);
		BEGIN();

		UPDATE(PojoUtils.from(o));

		SET(PojoUtils.foreach(PojoUtils.updateColumns(o), ",", true, true, "o"));

		WHERE(PojoUtils.foreach(PojoUtils.notNullColumns(m.get("w")), " AND ", true, true, "w"));
		return SQL();
	}

	public String delete(Object obj) {
		PojoUtils.caculationColumnList(obj);
		List<ColInfo> pks = PojoUtils.primaryKeys(obj);
		BEGIN();

		DELETE_FROM(PojoUtils.from(obj));

		WHERE(PojoUtils.foreach(pks, " AND ", true, true));
		return SQL();
	}

	public String deleteByWhere(Object obj) {
		PojoUtils.caculationColumnList(obj);
		List<ColInfo> cols = PojoUtils.notNullColumns(obj);
		BEGIN();

		DELETE_FROM(PojoUtils.from(obj));

		WHERE(PojoUtils.where(cols, " AND "));
		return SQL();
	}

	public String select(Object obj) {
		PojoUtils.caculationColumnList(obj);
		List<ColInfo> pks = PojoUtils.primaryKeys(obj);
		BEGIN();

		SELECT(PojoUtils.selectColumns(obj));

		FROM(PojoUtils.from(obj));

		WHERE(PojoUtils.foreach(pks, " AND ", true, true));
		return SQL();
	}

	public String selectById(Object obj) {
		@SuppressWarnings("rawtypes")
		Map oo = (Map) obj;
		BEGIN();

		SELECT("*");

		FROM(oo.get("table").toString());

		WHERE(oo.get("idname").toString() + "=" + oo.get("val").toString());
		return SQL();
	}

	public String selectAsterisk(Object obj) {
		PojoUtils.caculationColumnList(obj);
		List<ColInfo> cols = PojoUtils.notNullColumns(obj);
		BEGIN();

		SELECT("*");

		FROM(PojoUtils.from(obj));

		if(cols.size() > 0)
			WHERE(PojoUtils.where(cols, " AND "));
		return SQL();
	}

	public String selectByWhere(Object obj) {
		PojoUtils.caculationColumnList(obj);
		List<ColInfo> cols = PojoUtils.notNullColumns(obj);
		BEGIN();

		SELECT(PojoUtils.selectColumns(obj));

		FROM(PojoUtils.from(obj));

		if(cols.size() > 0)
			WHERE(PojoUtils.where(cols, " AND "));
		return SQL();
	}

	public String count(Object obj) {
		PojoUtils.caculationColumnList(obj);
		List<ColInfo> cols = PojoUtils.notNullColumns(obj);
		BEGIN();

		SELECT("COUNT(*)");

		FROM(PojoUtils.from(obj));

		if(cols.size() > 0)
			WHERE(PojoUtils.where(cols, " AND "));
		return SQL();
	}

	public String countAll(Object table) {
		BEGIN();
		SELECT("COUNT(*)");
		FROM(table.toString());
		return SQL();
	}

}
