package com.k2.Wiget.testAdapters;

import com.k2.Adapter.Adapter;
import com.k2.Adapter.annotation.Adapts;
import com.k2.Wiget.testTypes.TypeA;
import com.k2.Wiget.testTypes.TypeB;

@Adapts
public class AdaptA2B implements Adapter<TypeA, TypeB> {

	@Override public Class<TypeA> adaptFrom() { return TypeA.class; }
 	@Override public Class<TypeB> adaptTo() { return TypeB.class; }
 	@Override public TypeB adapt(TypeA from) { return new Adaption(from); }
 	@Override public Class<? extends TypeB> getAdapter() { return Adaption.class; }

 	private class Adaption extends TypeB  {
		
		private TypeA a;
		
		Adaption(TypeA a) {
			super(null, null, null);
			this.a = a;
		}

		@Override
		public Long getLongNumber() { return Long.valueOf(a.getNumber()); }

		@Override
		public String getName() { return a.getAlias(); }

		@Override
		public String getDescription() { return a.getTitle(); }
		
	}


}
