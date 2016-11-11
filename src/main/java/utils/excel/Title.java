package utils.excel;

import utils.excel.type.Str;
import utils.excel.type.Type;
import utils.excel.validate.mappers.RuleMapper;
import utils.excel.validate.rules.Rule;

import java.util.HashMap;
import java.util.Map;

public class Title<T> {
	
	private String name;
	
	private String fieldName;
	
	private Type toType;

	private Style titleStyle;
	
	private RuleMapper<T, Style> ruleMapper;
	//是否需要统计
	private Formula[] formulas;
	
	public Title(String name, String fieldName, Type toType, Style titleStyle, RuleMapper<T, Style> ruleMapper, Formula... formulas) {
		this.name = name;
		this.fieldName = fieldName;
		this.toType = toType != null ? toType : new Str(null, null);
		this.titleStyle = titleStyle;
		this.ruleMapper = ruleMapper;
		this.formulas = formulas;
	}

	public String getName() {
		return name;
	}

	public String getFieldName() {
		return fieldName;
	}

	public Type getToType() {
		return toType;
	}

	public Style getTitleStyle() {
		return titleStyle;
	}

	public Style mapper(T key) {
		if (null != ruleMapper)
			return ruleMapper.process(key);
		else
			return null;
	}

	public Formula[] getFormulas() {
		return formulas;
	}
}
