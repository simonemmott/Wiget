package com.k2.Wiget.templateFactory;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.k2.Util.classes.ClassUtil;
import com.k2.Wiget.templateFactory.types.FieldDef;
import com.k2.Wiget.templateFactory.types.MethodDef;
import com.k2.Wiget.templateFactory.types.TypeDef;

public class TypeDefImpl implements TypeDef, Comparable<TypeDef> {

	protected final String name;
	private final TypeDef collectionOf;
	public TypeDefImpl(Class<?> type) {
		this.name = type.getName();
		this.collectionOf = null;
	}
	public TypeDefImpl(String name) {
		this.name = name;
		this.collectionOf = null;
	}
	public TypeDefImpl(String name, TypeDef collectionOf) {
		this.name = name;
		this.collectionOf = collectionOf;
	}
	public TypeDefImpl(Class<?> type, TypeDef collectionOf) {
		this.name = type.getName();
		this.collectionOf = collectionOf;
	}
	@Override public int compareTo(TypeDef o) { return name.compareTo(o.getName()); }

	@Override public String getPackageName() { return ClassUtil.getPackageNameFromCanonicalName(name); }

	@Override public String getBaseName() { return ClassUtil.getBasenameFromCanonicalName(name); }

	@Override public String getName() { return name; }

	private List<FieldDef> fieldDefs = new ArrayList<FieldDef>();
	@Override public List<FieldDef> getFieldDefs() { return fieldDefs; }
	public TypeDefImpl add(FieldDef fieldDef) { fieldDefs.add(fieldDef); return this; }

	private List<MethodDef> methodDefs = new ArrayList<MethodDef>();
	@Override public List<MethodDef> getMethodDefs() { return methodDefs; }
	public TypeDefImpl add(MethodDef methodDef) {
		MethodDefImpl mdi = (MethodDefImpl)methodDef;
		mdi.declaringTypeDef = this;
		methodDefs.add(methodDef); 
		return this; 
	}

	@Override public boolean isCollection() { return (collectionOf != null); }

	@Override public TypeDef collectionOf() { return collectionOf; }
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (! (obj instanceof TypeDef))
			return false;
		TypeDef other = (TypeDef) obj;
		if (name == null) {
			if (other.getName() != null)
				return false;
		} else if (!name.equals(other.getName()))
			return false;
		return true;
	}
	
	

}
