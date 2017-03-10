package net.xssu.ipm.utils;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.xssu.ipm.annotation.Convert;
import net.xssu.ipm.annotation.JsonField;
import net.xssu.ipm.annotation.MyJson;
import net.xssu.ipm.annotation.Transient;

/**
 * @author bonult
 * @description 自定义序列化java对象工具
 * @version v1.0
 * @date 2016年11月5日 下午6:46:57
 */
public class Json {

	private boolean hasNull = false;

	public Json() {
		json = new StringBuilder();
	}

	public Json(Object obj) {
		json = new StringBuilder();
		appendObj(obj);
	}

	public Json(Object obj, boolean hasNull) {
		this.hasNull = hasNull;
		json = new StringBuilder();
		appendObj(obj);
	}

	public void clear() {
		json.delete(0, json.length());
	}

	public <T> T fromJson(String ojson, Class<T> clazz) {
		//		if(ojson == null || "null".equals(ojson))
		//			return null;
		//		Gson gg = new Gson();
		//		T xx = gg.fromJson(ojson, clazz);
		//		return xx;
		return null;
	}

	private Map<String,Convert> converters = new HashMap<String,Convert>();

	public Json registerConverter(String key, boolean removeOld, Convert tran) {
		if(removeOld || converters.get(key) == null)
			converters.put(key, tran);
		return this;
	}

	public Json registerConverter(String key, Convert tran) {
		if(converters.get(key) == null)
			converters.put(key, tran);
		return this;
	}

	public Json removeConverter(String key) {
		converters.remove(key);
		return this;
	}

	public boolean hasConverter(String key) {
		return converters.containsKey(key);
	}

	public Json setObj(Object obj) {
		json.delete(0, json.length());
		appendObj(obj);
		return this;
	}

	public void clearConverters() {
		converters.clear();
	}

	private Json appendNull() {
		json.append("null");
		return this;
	}

	public Json appendRawStr(String str) {
		json.append(str);
		return this;
	}

	public Json append(Object obj) {
		return appendObj(obj);
	}

	StringBuilder json = null;

	private Json appendObj(Object obj) {
		if(obj == null){
			appendNull();
		}else if(obj instanceof String){
			appendRawStr("\"").appendStr(obj.toString()).appendRawStr("\"");
		}else if(obj instanceof Number){
			json.append(((Number) obj).toString());
		}else if(obj instanceof List){
			appendList((List<?>) obj);
		}else if(obj instanceof Boolean){
			json.append(((Boolean) obj).toString());
		}else if(obj instanceof Set){
			appendSet((Set<?>) obj);
		}else if(obj instanceof Object[]){
			appendArray((Object[]) obj);
		}else if(obj instanceof Map){
			appendMap((Map<?,?>) obj);
		}else if(obj instanceof Date){
			json.append("\"" + obj.toString() + "\"");
		}else{
			if(obj.getClass().getAnnotation(MyJson.class) == null){
				appendRawStr(obj.toString());
			}else{
				json.append("{");
				Field[] fields = obj.getClass().getDeclaredFields();
				JsonField o;
				int i = 0;
				Convert tt;
				for(Field field : fields){
					if(field.getAnnotation(Transient.class) != null){
						continue;
					}else{
						if(!field.isAccessible()){
							field.setAccessible(true);
						}
						Object objj = null;
						try{
							objj = field.get(obj);
						}catch(Exception e){
							e.printStackTrace();
						}
						if(objj != null){
							if(i != 0)
								json.append(",");
							json.append("\"").append(field.getName()).append("\"");
							json.append(":");
							if((o = field.getAnnotation(JsonField.class)) != null && (tt = converters.get(o.convert())) != null){
								json.append(tt.transform(objj, o.convert(), o.note()));
							}else
								appendObj(objj);
							i++;
						}else if(hasNull){
							if(i != 0)
								json.append(",");
							json.append("\"").append(field.getName()).append("\"");
							json.append(":");
							json.append("null");
							i++;
						}
					}
				}
				json.append("}");
			}
		}
		return this;
	}

	private Json appendStr(String s) {
		for(int i = 0; i < s.length(); i++){
			char ch = s.charAt(i);
			switch(ch){
			case '"':
				json.append("\\\"");
				break;
			case '\\':
				json.append("\\\\");
				break;
			case '\b':
				json.append("\\b");
				break;
			case '\f':
				json.append("\\f");
				break;
			case '\n':
				json.append("\\n");
				break;
			case '\r':
				json.append("\\r");
				break;
			case '\t':
				json.append("\\t");
				break;
			default:
				if(ch >= '\u0000' && ch <= '\u001F'){
					String ss = Integer.toHexString(ch);
					json.append("\\u");
					for(int k = 0; k < 4 - ss.length(); k++){
						json.append('0');
					}
					json.append(ss.toUpperCase());
				}else{
					json.append(ch);
				}
			}
		}
		return this;
	}

	private Json appendArray(Object[] array) {
		json.append("[");
		if(array != null && array.length > 0){
			int x = 0;
			for(Object obj : array){
				if(obj != null || hasNull){
					if(x != 0)
						json.append(",");
					appendObj(obj);
					x++;
				}
			}
		}
		json.append("]");
		return this;
	}

	private Json appendList(List<?> list) {
		json.append("[");
		if(list != null && list.size() > 0){
			int x = 0;
			for(Object obj : list){
				if(obj != null || hasNull){
					if(x != 0)
						json.append(",");
					appendObj(obj);
					x++;
				}
			}
		}
		json.append("]");
		return this;
	}

	private Json appendMap(Map<?,?> map) {
		json.append("{");
		if(map != null && map.size() > 0){
			int x = 0;
			Object tmp;
			for(Object key : map.keySet()){
				tmp = map.get(key);
				if(tmp != null || hasNull){
					if(x != 0)
						json.append(",");
					appendObj(key);
					json.append(":");
					appendObj(tmp);
					x++;
				}
			}
		}
		json.append("}");
		return this;
	}

	private Json appendSet(Set<?> set) {
		json.append("[");
		if(set != null && set.size() > 0){
			int x = 0;
			for(Object obj : set){
				if(x != 0)
					json.append(",");
				appendObj(obj);
				x++;
			}
		}
		json.append("]");
		return this;
	}

	@Override
	public String toString() {
		return json.toString();
	}
	//private static Map<String,ClassInfo> classInfo = new ConcurrentHashMap<String,ClassInfo>();
	//	private static ClassInfo caculationProperties(Object obj) {
	//		String name = obj.getClass().getName();
	//		if(classInfo.containsKey(name))
	//			return classInfo.get(name);
	//		if(null == obj.getClass().getAnnotation(MyJson.class)){
	//			return null;
	//		}
	//		Field[] fields = obj.getClass().getDeclaredFields();
	//		Map<String,JsonField> props = new HashMap<String,JsonField>(fields.length/2+1);
	//		for(Field field : fields){
	//			JsonField jf = field.getAnnotation(JsonField.class);
	//			if(jf != null){
	//					props.put(field.getName(), jf);
	//			}else{
	//				Transient tra = field.getAnnotation(Transient.class);
	//				if(tra == null){
	//					props.put(field.getName(), DEFAULT_JSON_FIELD);
	//				}
	//			}
	//		}
	//		ClassInfo infs = new ClassInfo(obj.getClass().getSimpleName(), props);
	//		classInfo.put(name, infs);
	//		return infs;
	//	}
}
