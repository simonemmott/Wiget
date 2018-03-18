package com.k2.Wiget.templateFactory;

import java.util.ArrayList;
import java.util.List;

import com.k2.Util.classes.ClassUtil;
import com.k2.Wiget.templateFactory.types.ContainerDef;
import com.k2.Wiget.templateFactory.types.TemplateDef;
import com.k2.Wiget.templateFactory.types.TypeDef;

public class ContainerDefImpl implements ContainerDef {

	private String alias;
	public ContainerDefImpl(String alias) {
		this.alias = alias;
	}
	
	TemplateDef template;
	@Override public TemplateDef getTemplateDef() { return template; }

	@Override public String getAlias() { return alias; }


}
